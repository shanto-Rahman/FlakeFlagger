
import pandas as pd
import numpy as np
import warnings
import sys
from sklearn.feature_extraction.text import CountVectorizer
import time


#%%
def main_inspection(data):    
    
    if ('flaky_source' in data.columns):
        data = data.drop(['flaky_source'], axis=1)
    data = data.dropna()
    lst_project = get_specific_project_data(data,1)
    new_data = data[data['project'].isin(lst_project)]
    new_data = new_data.drop(['Unnamed: 0', 'testClassName', 'testMethodName'], axis=1)
    return new_data

def get_specific_project_data (data, k):
    list_of_projects = []
    for i in data.project.unique():
        temp = data.loc[data['project'] == i]
        non_flaky = temp['flaky'].value_counts()[0]
        flaky_total = len(temp) - non_flaky
        if (flaky_total >= k):
            list_of_projects.append(i)    
    return list_of_projects

#%%

def entropy_calculation(target_col):
    #Calculate the entropy of a dataset.
    elements,counts = np.unique(target_col,return_counts = True)
    entropy = np.sum([(-counts[i]/np.sum(counts))*np.log2(counts[i]/np.sum(counts)) for i in range(len(elements))])
    return entropy

def InfoGain(data,split_attribute_name,target_name):
    
    #Calculate the entropy of target value .. 
    total_entropy = entropy_calculation(data[target_name])
        
    #Calculate the values and the corresponding counts for the split attribute 
    vals,counts= np.unique(data[split_attribute_name],return_counts=True)
    #Calculate the weighted entropy
    Weighted_Entropy = np.sum([(counts[i]/np.sum(counts))*entropy_calculation(data.where(data[split_attribute_name]==vals[i]).dropna()[target_name]) for i in range(len(vals))])

    #Calculate the information gain
    Information_Gain = total_entropy - Weighted_Entropy
    
    return Information_Gain.round(3)
#%%

def vexctorizeTokenWithInformationGian(data,flakyStatus,IG_result):
    
    vocabualry_vectorizer = CountVectorizer()
    train = vocabualry_vectorizer.fit_transform(data)
    matrix_token = pd.DataFrame(train.toarray(), columns = vocabualry_vectorizer.get_feature_names())
    matrix_with_FlakyStatus = pd.concat([matrix_token, flakyStatus.reindex(matrix_token.index)], axis=1)
    
    # reduce the number of token in IG computation ..    
    matrix_with_FlakyStatus.drop([col for col, val in matrix_with_FlakyStatus.sum().iteritems() if val < 2], axis=1, inplace=True)

    counter = 0
    for col in matrix_with_FlakyStatus.columns:
        counter = counter + 1            
        if str(col) != 'flakyStatus':
            if (counter % 25 == 0):
                print (str(counter) + " tokens have been processed out of "+str(len(matrix_with_FlakyStatus.columns)))
            IG = InfoGain(matrix_with_FlakyStatus,col,'flakyStatus')          
            IG_result = IG_result.append(pd.Series([col,"token",IG], index=IG_result.columns ), ignore_index=True)        
    print ("Done from Token IG ... ")       
    return IG_result
    
    
#%%

def calculateOtherFeaturesIG(data,unwantedColumns,processedData,IG_result):
    new_data = data.copy()
    counter = 0
    print ("start other features ... ")
    for col in new_data:
        if col not in unwantedColumns:
            IG = InfoGain(new_data,col,'flakyStatus')
            counter = counter + 1
            if (counter % 5 == 0):
                print (str(counter) + " features have been processed ... ")
            if(col not in processedData):
                IG_result = IG_result.append(pd.Series([col,"JavaKeyWords",IG], index=IG_result.columns ), ignore_index=True)
            else:
                IG_result = IG_result.append(pd.Series([col,"FlakeFlagger",IG], index=IG_result.columns ), ignore_index=True)
    print ("Done from otherFeatures IG ... ")
    return IG_result
    
   
    

#%%
execution_time = time.time()

if __name__ == '__main__':
         
    vocabualry_and_processed_data = pd.read_csv(sys.argv[1])
    FlakeFlagger_features = ['assertion-roulette', 'conditional-test-logic', 'eager-test', 'fire-and-forget', 'indirect-testing', 'mystery-guest', 'resource-optimism', 'test-run-war', 'testLength', 'numAsserts', 'numCoveredLines', 'ExecutionTime', 'projectSourceLinesCovered', 'projectSourceClassesCovered', 'hIndexModificationsPerCoveredLine_window5', 'hIndexModificationsPerCoveredLine_window10', 'hIndexModificationsPerCoveredLine_window25', 'hIndexModificationsPerCoveredLine_window50', 'hIndexModificationsPerCoveredLine_window75', 'hIndexModificationsPerCoveredLine_window100', 'hIndexModificationsPerCoveredLine_window500', 'hIndexModificationsPerCoveredLine_window10000', 'num_third_party_libs']
    unwantedColumns = ['test_name', 'flakyStatus', 'tokenList', 'java_keywords','javaKeysCounter']

    IG_result_columns = ['features','type','IG']
    IG_result = pd.DataFrame(columns = IG_result_columns)    
    
    #IG for tokens only ...     
    IG_result = vexctorizeTokenWithInformationGian(vocabualry_and_processed_data['tokenList'],vocabualry_and_processed_data['flakyStatus'],IG_result)
    
    # IG for javakeywords and FlakeFlagger features...
    IG_result = calculateOtherFeaturesIG(vocabualry_and_processed_data,unwantedColumns,FlakeFlagger_features,IG_result)
    
    IG_result.to_csv('result/Information_gain_per_feature.csv',  index=False)
    
print("The processed is completed in : (%s) seconds. " % round((time.time() - execution_time), 5))
@Test public void addMultiValuedLong() throws RepositoryException {
  Node parentNode=getNode(TEST_PATH);
  Value[] values=new Value[2];
  values[0]=getAdminSession().getValueFactory().createValue(42L);
  values[1]=getAdminSession().getValueFactory().createValue(84L);
  parentNode.setProperty("multi long",values);
  parentNode.getSession().save();
  Session session2=createAnonymousSession();
  try {
    Property property=session2.getProperty(TEST_PATH + "/multi long");
    assertTrue(property.isMultiple());
    assertEquals(PropertyType.LONG,property.getType());
    Value[] values2=property.getValues();
    assertEquals(values.length,values2.length);
    assertEquals(values[0],values2[0]);
    assertEquals(values[1],values2[1]);
  }
  finally {
    session2.logout();
  }
}
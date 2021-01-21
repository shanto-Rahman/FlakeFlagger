@Test public void testQueryBinary(){
  String base=this.getClass().getName() + ".testQueryBinary-";
  for (int i=0; i < 10; i++) {
    String id=base + i;
    UpdateOp up=new UpdateOp(id,true);
    up.set("_id",id);
    up.set(NodeDocument.HAS_BINARY_FLAG,i % 2L);
    boolean success=super.ds.create(Collection.NODES,Collections.singletonList(up));
    assertTrue("document with " + id + " not created",success);
    removeMe.add(id);
  }
  List<String> result=getKeys(ds.query(Collection.NODES,base,base + "Z",NodeDocument.HAS_BINARY_FLAG,NodeDocument.HAS_BINARY_VAL,1000));
  assertEquals(5,result.size());
  assertTrue(result.contains(base + "1"));
  assertFalse(result.contains(base + "0"));
}
/** 
 * https://github.com/square/okhttp/issues/333 
 */
@Test public void headerBlockHasTrailingCompressedBytes512() throws Exception {
  String frame="gAMAAgAAAgkAAAABeLvjxqfCYgAAAAD//2IAAAAA//9iAAAAAP//YgQAAAD//2IAAAAA//9iAAAAAP/" + "/YgAAAAD//2IEAAAA//9KBAAAAP//YgAAAAD//2IAAAAA//9iAAAAAP//sgEAAAD//2IAAAAA\n//9iBAAAAP//Y" + "gIAAAD//2IGAAAA//9iAQAAAP//YgUAAAD//2IDAAAA//9iBwAAAP//4gAAAAD//+IEAAAA///iAgAAAP//4gYAA"+ "AD//+IBAAAA///iBQAAAP//4gMAAAD//+IHAAAA//8SAAAAAP//EgQAAAD//xICAAAA//8SBgAAAP//EgEAAAD//"+ "xIFAAAA//8SAwAAAP//EgcAAAD//5IAAAAA//+SBAAAAP//kgIAAAD//5IGAAAA//+SAQAAAP//kgUAAAD//5IDA"+ "AAA//+SBwAAAP//UgAAAAD//1IEAAAA//9SAgAAAP//UgYAAAD//1IBAAAA//9SBQAAAP//UgMAAAD//1IHAAAA/"+ "//SAAAAAP//0gQAAAD//9ICAAAA///SBgAAAP//0gEAAAD//9IFAAAA///SAwAAAP//0gcAAAD//zIAAAAA//8yB"+ "AAAAP//MgIAAAD//zIGAAAA//8yAQAAAP//MgUAAAD//zIDAAAA//8yBwAAAP//sgAAAAD//7IEAAAA//+yAgAAA"+ "P//sgYAAAD//w==";
  headerBlockHasTrailingCompressedBytes(frame,60);
}
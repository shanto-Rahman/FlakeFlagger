@Test public void testVEvent(){
  doTestResult("BEGIN:VCALENDAR\r\nBEGIN:VEVENT\r\nSUMMARY:foo\r\nDTSTART:20080504T123456Z\r\n" + "DTEND:20080505T234555Z\r\nEND:VEVENT\r\nEND:VCALENDAR","foo\nMay 4, 2008 12:34:56 PM\nMay 5, 2008 11:45:55 PM",ParsedResultType.CALENDAR);
  doTestResult("BEGIN:VEVENT\r\nSUMMARY:foo\r\nDTSTART:20080504T123456Z\r\n" + "DTEND:20080505T234555Z\r\nEND:VEVENT","foo\nMay 4, 2008 12:34:56 PM\nMay 5, 2008 11:45:55 PM",ParsedResultType.CALENDAR);
  doTestResult("BEGIN:VEVENT\r\nSUMMARY:foo\r\nDTSTART:20080504T123456\r\n" + "DTEND:20080505T234555\r\nEND:VEVENT","foo\nMay 4, 2008 12:34:56 PM\nMay 5, 2008 11:45:55 PM",ParsedResultType.CALENDAR);
  doTestResult("BEGIN:VEVENT\r\nSUMMARY:foo\r\nDTSTART:20080504\r\n" + "DTEND:20080505\r\nEND:VEVENT","foo\nMay 4, 2008\nMay 5, 2008",ParsedResultType.CALENDAR);
  doTestResult("BEGIN:VEVENT\r\nSUMMARY:foo\r\nDTSTART:20080504T123456Z\r\nEND:VEVENT","foo\nMay 4, 2008 12:34:56 PM",ParsedResultType.CALENDAR);
  doTestResult("BEGIN:VEVENT\r\nSUMMARY:foo\r\nDTSTART:20080504T123456\r\nEND:VEVENT","foo\nMay 4, 2008 12:34:56 PM",ParsedResultType.CALENDAR);
  doTestResult("BEGIN:VEVENT\r\nSUMMARY:foo\r\nDTSTART:20080504\r\nEND:VEVENT","foo\nMay 4, 2008",ParsedResultType.CALENDAR);
  doTestResult("BEGIN:VEVENT\r\nDTEND:20080505T\r\nEND:VEVENT","BEGIN:VEVENT\r\nDTEND:20080505T\r\nEND:VEVENT",ParsedResultType.URI);
  doTestResult("BEGIN:VEVENTSUMMARY:EventDTSTART:20081030T122030ZDTEND:20081030T132030ZEND:VEVENT","BEGIN:VEVENTSUMMARY:EventDTSTART:20081030T122030ZDTEND:20081030T132030ZEND:VEVENT",ParsedResultType.URI);
}
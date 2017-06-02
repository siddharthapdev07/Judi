//package com.agm.Judi.tests;
//
//import javax.mail.Authenticator;
//import javax.mail.Flags;
//import javax.mail.Folder;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Store;
//import javax.mail.internet.InternetAddress;
//import javax.mail.search.FromTerm;
//import javax.mail.search.SubjectTerm;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.util.Properties;
//
//public class MailHelper {
//
//	public static void main(String[] args) throws Exception {
//        Properties props = System.getProperties();
//        props.setProperty("mail.store.protocol", "imaps");
//
//            Session session = Session.getDefaultInstance(props, null);
//            Store store = session.getStore("imaps");
//            store.connect("imap.agmednet.com", "smylam@agmednet.com",
//                    "strPassword");
//
//            Folder folder = store.getFolder("INBOX");
//            folder.open(Folder.READ_WRITE);
//
//            System.out.println("Total Message:" + folder.getMessageCount());
//            System.out.println("Unread Message:"
//                    + folder.getUnreadMessageCount());
//            
//            Message[] messages = null;
//            boolean isMailFound = false;
//            Message mailFromXYZ= null;
//
//            //Search for mail from God
//            for (int i = 0; i < 5; i++) {
//                messages = folder.search(new SubjectTerm(
//                        "Welcome"),
//                        folder.getMessages());
//                //Wait for 10 seconds
//                if (messages.length == 0) {
//                    Thread.sleep(10000);
//                }
//            }
//
//            //Search for unread mail from God
//            //This is to avoid using the mail for which 
//            //Registration is already done
//            for (Message mail : messages) {
//                if (!mail.isSet(Flags.Flag.SEEN)) {
//                	mailFromXYZ = mail;
//                    System.out.println("Message Count is: "
//                            + mailFromXYZ.getMessageNumber());
//                    isMailFound = true;
//                }
//            }
//
//            //Test fails if no unread mail was found from God
//            if (!isMailFound) {
//                throw new Exception(
//                        "Could not find new mail from God :-(");
//            
//            //Read the content of mail and launch registration URL                
//            } else {
//                String line;
//                StringBuffer buffer = new StringBuffer();
//                BufferedReader reader = new BufferedReader(
//                        new InputStreamReader(mailFromXYZ
//                                .getInputStream()));
//                while ((line = reader.readLine()) != null) {
//                    buffer.append(line);
//                }
//                System.out.println(buffer);
//
//                //Your logic to split the message and get the Registration URL goes here
//                String registrationURL = buffer.toString().split("&amp;gt;http://www.god.de/members/?")[0]
//                        .split("href=")[1];
//                System.out.println(registrationURL);                            
//            }
//    }
//	
//	
//}

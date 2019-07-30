/******************************************************************************
  *  Compilation:  javac RandomImage.java
  *  Execution:    java RandomImage
  *  Dependencies: None
  *
  *  Generates a link to a random image, created by selecting a random
  *  word from a library of 10,001 English words and appending it to
  *  the standard Internet protocol suite.
  * 
  *  Version 1.01
  ******************************************************************************/
import java.net.*;
import java.io.*;

public class RandomImage {
  private static String link;
  
  public RandomImage() throws Exception {
    this.link = generateUsableImageLink();
  }
  
  public String getLink() {
    return this.link;
  }
  
  /**
   * Generates a link to an iamge parsed from the HTML
   * data of random sites.
   */
  private static String generateUsableImageLink() throws Exception {
    while(true) {
      String link = generatePossiblyUnusableLink();
      if(link.charAt(0) == 'h') {
        if(link.charAt(1) == 't') {
          if(link.charAt(2) == 't') {
            if(link.charAt(3) == 'p') {
              return link;
              
            }
          } 
        }
      }
    }
  }
  
  /**
   * Generates a parsed String ending in .png
   * or .jpg that may or may not be a usable image link.
   */
  private static String generatePossiblyUnusableLink() throws Exception{
    String html = generateHtmlWithImage();
    int endQuoteLocation = hasImage(html);
    String imageLink = retrieveImageLink(html, endQuoteLocation);
    return imageLink;
    
  }
  
  /**
   * Generates HTML data from a random website.
   */
  private static String generateHtmlWithImage() throws Exception {
    while(true) {
      String html = obtainWorkingHtml();
      if(hasImage(html) != -1) {
        return html;
        
      }
    }
  }
  
  /**
   * Takes in HTML data and the charactere place at
   * which an image file terminates (where the "g" in "jpg"
   * or "png" is).
   * @param html, a website's entire HTML data as a String
   * @param endQuoteLocation, the character place of the "g"
   * in an image element's "jpg" or "png" ending
   */
  private static String retrieveImageLink(String html, int endQuoteLocation) {
    int startQuoteLocation = 0;
    for(int i = endQuoteLocation - 4; i > endQuoteLocation - 1000; i--) {
      if(html.charAt(i) + 0 == 34) {
        startQuoteLocation = i + 1;
        break;
        
      }
    }
    String imageLink = "";
    for(int i = startQuoteLocation; i < endQuoteLocation + 1; i++) {
      imageLink = imageLink + html.charAt(i);
    }
    return imageLink;
    
  }
  
  /**
   * Checks if HTML data has an image.
   * @param html, any HTML data represented as a String
   */
  private static int hasImage(String html) {
    for(int i = 0; i < html.length(); i++) {
      // Is there a period?
      if(html.charAt(i) + 0 == 46) {
        // Is there a 'j'?
        if(html.charAt(i + 1) + 0 == 106) {
          // Is there a 'p'?
          if(html.charAt(i + 2) + 0 == 112) {
            // Is there a 'g'?
            if(html.charAt(i + 3) + 0 == 103) {
              return i + 3;
              
            }
          }
        }
        // Is there a 'p'?
        if(html.charAt(i + 1) + 0 == 112) {
          //Is there an 'n'?
          if(html.charAt(i + 2) + 0 == 110) {
            // Is there a 'g'?
            if(html.charAt(i + 3) + 0 == 103) {
              return i + 3;
              
            }
          }
        } 
      }
    }
    return -1;
    
  }
  
  /**
   * Generates random words from a list of 10001 words
   * until HTML data is parsed from a website made by
   * appending said random word to "http://www." and ".com"
   */
  private static String obtainWorkingHtml() throws Exception {
    while(true) {
      try {
        String randomUrl = "https://www." + randomWord() + ".com";
        return getHtmlFromUrl(randomUrl);
        
      } catch(Exception e) {
      }
    }
  }
    
  /**
   * Retrieves HTML data from a given link, if available.
   * @param url, the link to a website
   */
  private static String getHtmlFromUrl(String url) throws Exception {
    URL urlObj = new URL(url);
    BufferedReader in = new BufferedReader(new InputStreamReader(urlObj.openStream()));
    String html = ""; 
    String inputLine;
    while ((inputLine = in.readLine()) != null) {
      html = html + inputLine;
    }
    in.close();
    return html;
    
  }
  
  /**
   * Generates a random word from a text file of
   * 10,001 English words.
   */
  private static String randomWord() {
    In inStream = new In("10001words.txt");
    String allWords = inStream.readAll();
    int rndmLineBreaks = (int) (Math.random() * 10002);
    int firstLetter = getFirstLetter(rndmLineBreaks, allWords);
    int lastLetter = getLastLetter(firstLetter, allWords);
    String rndmWord = "";
    for(int i = firstLetter; i < lastLetter + 1; i++) {
      rndmWord = rndmWord + allWords.charAt(i);
    }
    return rndmWord;
    
  }
  
  /**
   * Finds the character place of the first character of a word chosen after
   * a random number of words or line break characters.
   * @param numLineBreaks, the number of line break characters to be passed
   * before reaching the word
   * @param text, the text being parsed
   */
  private static int getFirstLetter(int numLineBreaks, String text) {
    int lineBreakCounter = 0;
    for(int i = 0; i < text.length(); i++) {
      if(text.charAt(i) + 0 == 10) {
        lineBreakCounter++;
      }
      if(lineBreakCounter == numLineBreaks) {
        return i + 1;
        
      }
    }
    return -1;
    
  }
  
  /**
   * Finds the character place of the last character of a word
   * chosen after a random numer of words or line break characters.
   * @param firstLetter, the character place of the first character
   * in a given word
   * @param text, the text in which that word occurs
   */
  private static int getLastLetter(int firstLetter, String text) {
    for(int i = firstLetter; i < firstLetter + 20; i++) {
      if(text.charAt(i) + 0 == 10) {
        return i - 1;
        
      }
    }
    return -1;
    
  } 
}
/******************************************************************************
  *  Compilation:  javac RandomImage.java
  *  Execution:    java RandomImage
  *  Dependencies: None
  *
  *  Generates a link to a random image, created by selecting a random
  *  word from a library of 10,001 English words and appending it to
  *  the standard Internet protocol suite.
  * 
  ******************************************************************************/
import java.net.*;
import java.io.*;

public class RandomImage {
  public static String link;
  
  public RandomImage() throws Exception {
    this.link = generateUsableImageLink();
  }
  
  /*
   * Description: Generates a link to an image
   *              parsed from the HTML data
   *              of random sites. 
   *                         
   * Inputs: None. 
   *          
   * Output: A String, of the form "http://...png"
   *         or "http://...jpg"
   */
  public static String generateUsableImageLink() throws Exception {
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
  
  /*
   * Description: Generates a parsed String ending in 
   *              .png or .jpg that may or may not
   *              be a usable link.     
   * 
   * Inputs: None. 
   *          
   * Output: A String, of the form "...png" or
   *         "...jpg"
   */ 
  public static String generatePossiblyUnusableLink() throws Exception{
    String html = generateHtmlWithImage();
    int endQuoteLocation = hasImage(html);
    String imageLink = retrieveImageLink(html, endQuoteLocation);
    return imageLink;
    
  }
  
  /*
   * Description: Generates HTML data from
   *              a random website.
   *                          
   * Inputs: None.
   *          
   * Output: A String, containing all of the HTML 
   *         data parsed from a random website.
   */
  public static String generateHtmlWithImage() throws Exception {
    while(true) {
      String html = obtainWorkingHtml();
      if(hasImage(html) != -1) {
        return html;
        
      }
    }
  }
  
  /*
   * Description: Takes in HTML data and the character
   *              place at which an image file terminates
   *              (where the "g" in "jpg" or "png" is)
   * 
   * Inputs: A String, A Website's entire HTML data,
   *         An integer: The character place of the "g"
   *         in an image element's "jpg" or "png" ending),
   *         
   * Output: A String, the link to the image found in the
   *         HTML String, which ends at the inputted integer.
   */
  public static String retrieveImageLink(String html, int endQuoteLocation) {
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
  
  /*
   * Description: Checks if HTML data has an image.
   * 
   * Inputs: A String, HTML data. 
   *          
   * Output: An integer, -1 indicating that the
   *         inputted HTML has no image link. Any
   *         other integer indicating the character
   *         place at which that image link ends
   *         (the "g" in "png" or "jpg")
   */
  public static int hasImage(String html) {
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
  
  /*
   * Description: Generates random words until
   *              HTML data is parsed from a website
   *              made by appending said random word.    
   * 
   * Inputs: None. 
   *          
   * Output: A String, random HTML data.
   */
  public static String obtainWorkingHtml() throws Exception {
    while(true) {
      try {
        String randomUrl = "https://www." + randomWord() + ".com";
        return getHtmlFromUrl(randomUrl);
        
      } catch(Exception e) {
      }
    }
  }
  
  /*
   * Description: Retrieves HTML data
   *              from a given link, if available.
   *                            
   * Inputs: A String, the link to a website. 
   *          
   * Output: A String, HTML data.
   */ 
  public static String getHtmlFromUrl(String url) throws Exception {
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
  
  /*
   * Description: Generates a random word from
   *              a text file of 10,001 English
   *              words.                         
   * 
   * Inputs: None.
   *          
   * Output: A String, a random word.
   */
  public static String randomWord() {
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
  
  /*
   * Description: Finds the character place of the first
   *              character of a word chosen after a random
   *              number of words or line break characters.
   *                      
   * Inputs:  An integer, the number of line break characters
   *          to be passed before reaching the word. A String,
   *          the text in question.
   * 
   * Output: An integer, the character place of the first character in a 
   *         randomly selected word.
   */
  public static int getFirstLetter(int numLineBreaks, String text) {
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
  
  /*
   * Description: Finds the character place of the last
   *              character of a word chosen after a random
   *              number of words or line break characters.    
   * 
   * Inputs: An integer, the character place of the first 
   *         character in a given word. A String, the text in which 
   *         that word occurs.
   * 
   * Output: An integer, the character place of the last character in 
   *         a randomly selected word. 
   */
  public static int getLastLetter(int firstLetter, String text) {
    for(int i = firstLetter; i < firstLetter + 20; i++) {
      if(text.charAt(i) + 0 == 10) {
        return i - 1;
        
      }
    }
    return -1;
    
  } 
}
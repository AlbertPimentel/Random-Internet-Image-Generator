/******************************************************************************
 *  Compilation:  javac DrawImages.java
 *  Execution:    java DrawImages
 *  Dependencies: RandomImage
 *
 *  Draws 9 random images parsed from the HTML data of over 10,000 possible
 *  websites.
 *
 ******************************************************************************/
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Toolkit;
import java.awt.Dimension;

public class DrawImages {
  /*
   * Description: Draws 9 images which were parsed from the HTML
   *              data of over 10,000 possible websites, found by
   *              appending random English words to the standard
   *              application protocol.
   *                         
   * Inputs: A String array.
   *          
   * Output: None.
   */
  public static void main(String[] args) throws Exception {
    System.setProperty("http.agent", "Chrome");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = (int) screenSize.getWidth();
    double height = (int) screenSize.getHeight();
    PennDraw.setCanvasSize((int) width, (int) height); 
    PennDraw.setXscale(0, width);
    PennDraw.setYscale(0, height);
    
    for(int i = 0; i < 3; i++) {
      for(int j = 0; j < 3; j++) {
        drawImage((width / 6) + i * (width / 3), (height / 6) + j * (height / 3),
                  width / 3, height / 3);
      }
    }
  }
  
  /*
   * Description: 
   *              
   *                         
   * Inputs: Four doubles, indicating the images x and y positions,
   *         and width and height, in pixels.
   *          
   * Output: None.
   */
  public static void drawImage(double xPos, 
                               double yPos, double width, double height) throws Exception {
    while(true) {
      try { RandomImage img = new RandomImage();
      URL url = new URL(img.link);
      Image image = ImageIO.read(url.openStream());
      PennDraw.picture(xPos, yPos, image, width, height, 0);
      return;
    }
    catch(Exception e) {
    }
  }
  }
  
}
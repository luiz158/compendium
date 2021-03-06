import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

/**
 * Program demonstrujący tworzenie obrazu
 * z pojedynczych pikseli.
 * @version 1.13 2007-08-16
 * @author Cay Horstmann
 */
public class RasterImageTest
{
   public static void main(String[] args)
   {
      EventQueue.invokeLater(new Runnable()
         {
            public void run()
            {
               JFrame frame = new RasterImageFrame();
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               frame.setVisible(true);
            }
         });
   }
}

/**
 * Ramka wyświetlająca zbiór Mandelbrota.
 */
class RasterImageFrame extends JFrame
{
   public RasterImageFrame()
   {
      setTitle("RasterImageTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
      BufferedImage image = makeMandelbrot(DEFAULT_WIDTH, DEFAULT_HEIGHT);
      add(new JLabel(new ImageIcon(image)));
   }

   /**
    * Tworzy obraz zbioru Mandelbrota.
    * @param width szerokość
    * @parah height wysokość
    * @return obraz
    */
   public BufferedImage makeMandelbrot(int width, int height)
   {
      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      WritableRaster raster = image.getRaster();
      ColorModel model = image.getColorModel();

      Color fractalColor = Color.red;
      int argb = fractalColor.getRGB();
      Object colorData = model.getDataElements(argb, null);

      for (int i = 0; i < width; i++)
         for (int j = 0; j < height; j++)
         {
            double a = XMIN + i * (XMAX - XMIN) / width;
            double b = YMIN + j * (YMAX - YMIN) / height;
            if (!escapesToInfinity(a, b)) raster.setDataElements(i, j, colorData);
         }
      return image;
   }

   private boolean escapesToInfinity(double a, double b)
   {
      double x = 0.0;
      double y = 0.0;
      int iterations = 0;
      while (x <= 2 && y <= 2 && iterations < MAX_ITERATIONS)
      {
         double xnew = x * x - y * y + a;
         double ynew = 2 * x * y + b;
         x = xnew;
         y = ynew;
         iterations++;
      }      
      return x > 2 || y > 2;
   }

   private static final double XMIN = -2;
   private static final double XMAX = 2;
   private static final double YMIN = -2;
   private static final double YMAX = 2;
   private static final int MAX_ITERATIONS = 16;
   private static final int DEFAULT_WIDTH = 400;
   private static final int DEFAULT_HEIGHT = 400;
}

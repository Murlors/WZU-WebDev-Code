package action;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

@WebServlet("/captchaServlet")
public class CaptchaServlet extends HttpServlet {
    private static final int IMAGE_WIDTH = 83;
    private static final int IMAGE_HEIGHT = 30;
    private static final int FONT_SIZE = 24;
    private static final int NUM_OF_CHARACTERS = 4;

    private Font getFont() {
        Random random = new Random();
        Font[] font = {
                new Font("Ravie", Font.PLAIN, FONT_SIZE),
                new Font("Antique Olive Compact", Font.PLAIN, FONT_SIZE),
                new Font("Forte", Font.PLAIN, FONT_SIZE),
                new Font("Wide Latin", Font.PLAIN, FONT_SIZE),
                new Font("Gill Sans Ultra Bold", Font.PLAIN, FONT_SIZE)
        };
        return font[random.nextInt(font.length)];
    }

    private String drawRandomString(Graphics g, char[] characters) {
        StringBuilder sRand = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < NUM_OF_CHARACTERS; i++) {
            g.setFont(getFont());
            String rand = Character.toString(characters[random.nextInt(characters.length)]);
            sRand.append(rand);
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            g.drawString(rand, 20 * i + 6, 25);
        }
        return sRand.toString();
    }

    private void drawRandomDots(Graphics g) {
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int x1 = random.nextInt(IMAGE_WIDTH);
            int y1 = random.nextInt(IMAGE_HEIGHT);
            g.drawOval(x1, y1, 2, 2);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpeg");

        try (OutputStream os = response.getOutputStream()) {
            BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_BGR);
            Graphics g = image.getGraphics();
            g.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);

            char[] characters = "abcdefghigklmnopqrstuvwxyz2345678901".toCharArray();
            String captcha = drawRandomString(g, characters);

            request.getSession().setAttribute("captcha", captcha);
            drawRandomDots(g);

            g.dispose();
            ImageIO.write(image, "JPEG", os);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
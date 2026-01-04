package src.technical;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * Utility class for loading and displaying images from URLs
 * Supports Unsplash, direct image URLs, and handles YouTube video URLs
 */
public class ImageUtil {
    
    /**
     * Load image from URL and resize it
     * Handles Unsplash URLs, direct image URLs, and YouTube thumbnails
     */
    public static ImageIcon loadImageIcon(String imageURL, int width, int height) {
        if (imageURL == null || imageURL.trim().isEmpty() || 
            imageURL.equals("null") || imageURL.equalsIgnoreCase("null")) {
            System.out.println("ImageUtil: Empty or null URL provided");
            return createPlaceholderIcon(width, height, "No Image URL");
        }
        
        // Normalize the URL
        String normalizedURL = normalizeImageURL(imageURL.trim());
        System.out.println("ImageUtil: Original URL: " + imageURL);
        System.out.println("ImageUtil: Normalized URL: " + normalizedURL);
        
        if (!isValidURL(normalizedURL)) {
            System.out.println("ImageUtil: Invalid URL format");
            return createPlaceholderIcon(width, height, "Invalid URL");
        }
        
        try {
            // Handle Unsplash URLs specially
            if (normalizedURL.contains("unsplash.com/photos/")) {
                // Try to extract image from Unsplash photo page
                // For Unsplash, we can try accessing the download link or use a workaround
                String photoId = normalizedURL.substring(normalizedURL.lastIndexOf("/") + 1);
                if (photoId.contains("?")) photoId = photoId.substring(0, photoId.indexOf("?"));
                
                // Try multiple Unsplash URL formats
                String[] unsplashFormats = {
                    "https://source.unsplash.com/" + photoId + "/800x600",
                    "https://images.unsplash.com/photo-" + photoId + "?w=800&auto=format&fit=crop",
                    normalizedURL // Try original URL as last resort
                };
                
                for (String tryURL : unsplashFormats) {
                    try {
                        System.out.println("ImageUtil: Trying Unsplash URL: " + tryURL);
                        java.net.URI uri = new java.net.URI(tryURL);
                        URL url = uri.toURL();
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setConnectTimeout(8000);
                        connection.setReadTimeout(8000);
                        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
                        connection.setRequestProperty("Accept", "image/*");
                        
                        int responseCode = connection.getResponseCode();
                        System.out.println("ImageUtil: Response code: " + responseCode);
                        
                        if (responseCode == 200) {
                            BufferedImage image = ImageIO.read(connection.getInputStream());
                            connection.disconnect();
                            
                            if (image != null) {
                                System.out.println("ImageUtil: Successfully loaded image: " + image.getWidth() + "x" + image.getHeight());
                                // Resize and return
                                double aspectRatio = (double) image.getWidth() / image.getHeight();
                                int newWidth = width;
                                int newHeight = height;
                                
                                if (aspectRatio > 1) {
                                    newHeight = (int) (width / aspectRatio);
                                } else {
                                    newWidth = (int) (height * aspectRatio);
                                }
                                
                                Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                                BufferedImage bufferedScaled = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
                                Graphics2D g2d = bufferedScaled.createGraphics();
                                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                                g2d.drawImage(scaledImage, 0, 0, null);
                                g2d.dispose();
                                
                                return new ImageIcon(bufferedScaled);
                            }
                        }
                        connection.disconnect();
                    } catch (Exception e) {
                        System.out.println("ImageUtil: Failed to load from " + tryURL + ": " + e.getMessage());
                        // Continue to next format
                    }
                }
            }
            
            // Set timeout for connection
            java.net.URI uri = new java.net.URI(normalizedURL);
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
            
            BufferedImage image = ImageIO.read(connection.getInputStream());
            connection.disconnect();
            
            if (image == null) {
                return createPlaceholderIcon(width, height, "Failed to Load");
            }
            
            // Resize image while maintaining aspect ratio
            double aspectRatio = (double) image.getWidth() / image.getHeight();
            int newWidth = width;
            int newHeight = height;
            
            if (aspectRatio > 1) {
                // Landscape
                newHeight = (int) (width / aspectRatio);
            } else {
                // Portrait
                newWidth = (int) (height * aspectRatio);
            }
            
            Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            BufferedImage bufferedScaled = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bufferedScaled.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();
            
            return new ImageIcon(bufferedScaled);
            
        } catch (IOException e) {
            System.err.println("Error loading image from URL: " + normalizedURL);
            System.err.println("Error: " + e.getMessage());
            return createPlaceholderIcon(width, height, "Load Error");
        } catch (Exception e) {
            System.err.println("Invalid image URL: " + normalizedURL);
            System.err.println("Error: " + e.getMessage());
            return createPlaceholderIcon(width, height, "Invalid URL");
        }
    }
    
    /**
     * Normalize image URLs - convert Unsplash photo URLs to direct image URLs
     * Handle YouTube URLs by extracting thumbnail
     */
    private static String normalizeImageURL(String url) {
        // Handle Unsplash photo URLs
        // For Unsplash, we'll try to get the image from the photo page
        // The photo ID is in the URL, but we need to access it differently
        if (url.contains("unsplash.com/photos/")) {
            String photoId = url.substring(url.lastIndexOf("/") + 1);
            // Remove any query parameters or fragments
            if (photoId.contains("?")) {
                photoId = photoId.substring(0, photoId.indexOf("?"));
            }
            if (photoId.contains("#")) {
                photoId = photoId.substring(0, photoId.indexOf("#"));
            }
            
            // Try multiple Unsplash image URL formats
            // Format 1: Direct Unsplash image URL (most reliable)
            // Note: This may require API access, so we'll try the photo page first
            // For now, return a placeholder that indicates Unsplash URL
            // In production, you might want to use Unsplash API with client ID
            return url; // Return original - will try to load from page
        }
        
        // Handle YouTube URLs - get thumbnail
        if (url.contains("youtube.com/watch") || url.contains("youtu.be/")) {
            String videoId = extractYouTubeId(url);
            if (videoId != null) {
                // Get high-quality thumbnail
                return "https://img.youtube.com/vi/" + videoId + "/maxresdefault.jpg";
            }
        }
        
        // Return original URL if no special handling needed
        return url;
    }
    
    /**
     * Extract YouTube video ID from URL
     */
    private static String extractYouTubeId(String url) {
        String videoId = null;
        if (url.contains("youtube.com/watch?v=")) {
            int start = url.indexOf("v=") + 2;
            int end = url.indexOf("&", start);
            if (end == -1) end = url.length();
            videoId = url.substring(start, end);
        } else if (url.contains("youtu.be/")) {
            int start = url.indexOf("youtu.be/") + 9;
            int end = url.indexOf("?", start);
            if (end == -1) end = url.length();
            videoId = url.substring(start, end);
        }
        return videoId;
    }
    
    /**
     * Create a placeholder icon when image is not available
     */
    private static ImageIcon createPlaceholderIcon(int width, int height, String message) {
        BufferedImage placeholder = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = placeholder.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw gradient background
        g2d.setPaint(new GradientPaint(0, 0, new Color(245, 245, 245), 
                                       0, height, new Color(230, 230, 230)));
        g2d.fillRect(0, 0, width, height);
        
        // Draw border
        g2d.setColor(new Color(200, 200, 200));
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawRect(5, 5, width - 11, height - 11);
        
        // Draw icon (camera/image icon representation)
        int iconSize = Math.min(width, height) / 3;
        int iconX = (width - iconSize) / 2;
        int iconY = (height - iconSize) / 2 - 15;
        
        g2d.setColor(new Color(180, 180, 180));
        g2d.setStroke(new BasicStroke(3));
        // Draw a simple camera/photo icon
        g2d.drawRect(iconX, iconY, iconSize, iconSize * 2 / 3);
        g2d.fillOval(iconX + iconSize / 2 - 5, iconY + iconSize / 3 - 5, 10, 10);
        
        // Draw text
        g2d.setColor(new Color(120, 120, 120));
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        FontMetrics fm = g2d.getFontMetrics();
        String text = message != null ? message : "No Image";
        int textWidth = fm.stringWidth(text);
        int textY = iconY + iconSize + 25;
        g2d.drawString(text, (width - textWidth) / 2, textY);
        
        g2d.dispose();
        return new ImageIcon(placeholder);
    }
    
    /**
     * Check if URL is valid
     */
    private static boolean isValidURL(String url) {
        try {
            java.net.URI uri = new java.net.URI(url);
            return uri.getScheme() != null && 
                   (uri.getScheme().equals("http") || uri.getScheme().equals("https"));
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Load image icon with default size
     */
    public static ImageIcon loadImageIcon(String imageURL) {
        return loadImageIcon(imageURL, 400, 250);
    }
}


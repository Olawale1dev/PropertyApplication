package com.example.property.security;

/*
@WebServlet(name = " FileUploadServletExample ", urlPatterns = {"/post"})
@MultipartConfig
public class FileUploadServletExample extends HttpServlet {
    private final static Logger LOGGER =
            Logger.getLogger(Property.class.getCanonicalName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // creating path components for saving the file
        final String path = request.getParameter("destination");
        final Part filePart = request.getPart();
        final String fileName = getFileName(filePart);

        // declare instances of OutputStream, InputStream, and PrintWriter classes
        OutputStream otpStream = null;
        InputStream iptStream = null;
        final PrintWriter writer = response.getWriter();

        // try section handles the code for storing file into the specified location
        try {
            // initialize instances of OutputStream and InputStream classes
            otpStream = new FileOutputStream(new File(path + File.separator + fileName));
            iptStream = filePart.getInputStream();

            int read = 0;

            // initialize bytes array for storing file data
            final byte[] bytes = new byte[1024];

            // use while loop to read data from the file using iptStream and write into  the desination folder using writer and otpStream
            while ((read = iptStream.read(bytes)) != -1) {
                otpStream.write(bytes, 0, read);
            }
            writer.println("New file " + fileName + " created at " + path);
            LOGGER.log(Level.INFO, "File{0}being uploaded to {1}", new Object[]{fileName, path});
        }
        // catch section handles the errors
        catch (FileNotFoundException fne){
            writer.println("You either did not specify a file to upload or are trying to upload a file to a protected or nonexistent location.");
            writer.println("<br/> ERROR: " + fne.getMessage());
            LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}", new Object[]{fne.getMessage()});
        }
        // finally section will close all the open classes
        finally {
            if (otpStream != null) {
                otpStream.close();
            }
            if (iptStream != null) {
                iptStream.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }

    private String getFileName(String file) {
        return file;
    }

    // getFileName() method to get the file name from the part
    private String getFileName(final Part part) {
        // get header(content-disposition) from the part
        final String partHeader = part.getHeader("content-disposition");
        LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);

        // code to get file name from the header
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        // it will return null when it doesn't get file name in the header
        return null;
    }
}
*/

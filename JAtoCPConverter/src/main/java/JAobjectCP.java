import java.io.*;
import java.util.*;

public class JAobjectCP {

    public static void main(String[] args) {
        String folderPath = "C:\\JAtoCP\\test";
        
        processFilesInFolder(new File(folderPath));
    }

    private static void processFilesInFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    processFilesInFolder(file);
                } else if (file.getName().equals("object.json")) {
                    processTreeFile(file);
                }
            }
        }
    }

    private static void processTreeFile(File file) {
        try (Scanner scanner = new Scanner(file)) {
            // Read the required fields from the file
        	String line="";
        	String Name = "";
        	String Category = "";
        	String DisplayName = "";
        	String Description = "";
        	String Price = "";
        	String Edibility = "";
        	StringBuilder contextTagsBuilder = new StringBuilder();
        	while (scanner.hasNextLine()) {
        	    line = scanner.nextLine().trim().replaceAll("\\s+", " ");
        	    
        	    if (line.contains("ContextTags")) {
                    contextTagsBuilder.append(line.substring(line.indexOf("[") + 1));
                    while (scanner.hasNextLine()) {
                        line = scanner.nextLine().trim();
                        contextTagsBuilder.append(line);
                        if (line.contains("]")) {
                            break;
                        }
                    }
                } else {
        		String[] parts = line.split(":");
        		if (parts.length >= 2) {
        		    String part2 = parts[1].trim();
        		    if (part2.startsWith("\"")) {
        		        part2 = part2.substring(1);
        		    }
        		    if (part2.endsWith("\"")) {
        		        part2 = part2.substring(0, part2.length() - 1);
        		    }
        		    if (part2.endsWith(",")) {
        		        part2 = part2.substring(0, part2.length() - 1);
        		    }
        		    if (part2.endsWith("\"")) {
        		        part2 = part2.substring(0, part2.length() - 1);
        		    }
        		
        		
 		   System.out.println(line);
        	   if (line.contains("Name") && !line.contains("Localization")) {
        		   DisplayName = part2;
        		   Name = part2.replace(" ", "_");
        		   System.out.println(Name);
                }
        	   if (line.contains("Description") && !line.contains("Localization")) {
        		   Description = part2;
        		   System.out.println(Description);
                }
        	   if (line.contains("Category")) {
        		   Category  = part2;
        		   System.out.println(Category);
                }
        	   if (line.contains("Edibility")) {
        		   Edibility = part2;
        		   System.out.println(Edibility);
                }
        	   if (line.contains("Price")) {
        		   Price = part2;
        		   System.out.println(Price);
                }
        		}
                }
        	}
            String saplingPurchasePrice = "6000";
            String contextTags = contextTagsBuilder.toString();
            if (contextTags.equals("") || contextTags==null) {
            	contextTags = "\"food_item\"";
            }
            int Seedsprice = Integer.valueOf(Price)*2/3;

            try (PrintWriter writer = new PrintWriter(file)) {
                writer.print(""); 
            }

            String jsonOutput = ""+
            		 "{\n" +
                    "\t\"Changes\": [\n" +
                    "\t\t{\n" +
                    "\t\t\t\"Action\": \"EditData\",\n" +
                    "\t\t\t\"Target\": \"Data/Objects\",\n" +
                    "\t\t\t\"Entries\": {\n" +
                    "\t\t\t\t\"zzzCustom.crops_" + Name + "\": {\n" +
                    "\t\t\t\t\t\"Name\": \"zzzCustom.crops_" + Name + "\",\n" +
                    "\t\t\t\t\t\"Type\": \""+Category+"\",\n" +
                    "\t\t\t\t\t\"Category\": -75,\n" +
                    "\t\t\t\t\t\"DisplayName\": \"" + DisplayName + "\",\n" +
                    "\t\t\t\t\t\"Description\": \"" + Description + "\",\n" +
                    "\t\t\t\t\t\"Edibility\": "+Edibility+",\n" +
                    "\t\t\t\t\t\"Price\": " + Price + ",\n" +
                    "\t\t\t\t\t\"Texture\": \"{{InternalAssetKey: assets/Crops.png}}\",\n" +
                    "\t\t\t\t\t\"SpriteIndex\": 0,\n" +
                    "\t\t\t\t\t\"ContextTags\": [" + contextTags + "],\n" +
                    "\t\t\t\t},\n" ;
                    
            // Write the reformatted data back to the file
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.print(jsonOutput);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
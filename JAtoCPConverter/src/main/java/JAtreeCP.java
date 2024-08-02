import java.io.*;
import java.util.*;

public class JAtreeCP {

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
                } else if (file.getName().equals("tree.json")) {
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
        	String Fruit = "";
        	String DisplayName = "";
        	String Description = "";
        	String Season = "";
        	while (scanner.hasNextLine()) {
        	    line = scanner.nextLine().trim().replaceAll("\\s+", " ");
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
        	   if (line.contains("Name") && !line.contains("SaplingName") && !line.contains("Localization")) {
        		   Name = part2.replace(" ", "_");
        		   System.out.println(Name);
                }
        	   if (line.contains("Product")) {
        		   Fruit = part2.replace(" ", "_");
        		   System.out.println(Fruit);
                }
        	   if (line.contains("SaplingName")) {
        		   DisplayName  = part2;
        		   System.out.println(DisplayName);
                }
        	   if (line.contains("SaplingDescription")) {
        		   Description = part2;
        		   System.out.println(Description);
                }
        	   if (line.contains("Season")) {
        		   Season = part2;
        		   System.out.println(Season);
                }
        	   if (line.contains("Localization")) {
        		   	break;
                }
        		}
        	}
            String saplingPurchasePrice = "6000";

            try (PrintWriter writer = new PrintWriter(file)) {
                writer.print(""); 
            }

            String jsonOutput = "{\n" +
                    "\t\t\t\t\"zzzCustom.crops_" + Name + "\": {\n" +
                    "\t\t\t\t\t\"DisplayName\": \"" + DisplayName + "\",\n" +
                    "\t\t\t\t\t\"Seasons\": [ \"" + Season + "\" ],\n" +
                    "\t\t\t\t\t\"Fruit\": [{\"ItemId\": \"" + Fruit + "\"}],\n" +
                    "\t\t\t\t\t\"Texture\": \"{{InternalAssetKey: assets/FruitTrees.png}}\",\n" +
                    "\t\t\t\t\t\"TextureSpriteRow\": 0\n" +
                    "\t\t\t\t},\n" +
                    "\t\t\t\t\n" +
                    "\t\t\t\t\"" + Fruit + "_Sapling\": {\n" +
                    "\t\t\t\t\t\"Name\": \"" + DisplayName + "\",\n" +
                    "\t\t\t\t\t\"Type\": \"Basic\",\n" +
                    "\t\t\t\t\t\"Category\": -74,\n" +
                    "\t\t\t\t\t\"DisplayName\": \"" + DisplayName + "\",\n" +
                    "\t\t\t\t\t\"Description\": \"" + Description + "\",\n" +
                    "\t\t\t\t\t\"Texture\": \"{{InternalAssetKey: assets/TreeObjects.png}}\",\n" +
                    "\t\t\t\t\t\"SpriteIndex\": 0,\n" +
                    "\t\t\t\t\t\"Price\": " + saplingPurchasePrice + ",\n" +
                    "\t\t\t\t\t\"ContextTags\": [\"fruittree_sapling\"]\n" +
                    "\t\t\t\t},\n" +
                    "\t\t\t}\n";
                    
            // Write the reformatted data back to the file
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.print(jsonOutput);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
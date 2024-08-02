import java.io.*;
import java.util.*;

public class JAcropCP {

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
                } else if (file.getName().equals("crop.json")) {
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
        	String HarvestItemId = "";
        	String DisplayName = "";
        	String Description = "";
        	String Seasons = "";
        	String DaysInPhase = "";
        	String Price = "";
        	String Raised = "false";
        	String Regrow = "";
        	String HarvestMethod = "false";
        	String HarvestMinStack = "1";
        	String HarvestMaxStack = "1";
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
        	   if (line.contains("Product")) {
        		   HarvestItemId  = part2;
        		   HarvestItemId = part2.replace(" ", "_");
        		   System.out.println(HarvestItemId);
                }
        	   if (line.contains("Seasons")) {
        		   Seasons = part2;
        		   System.out.println(Seasons);
                }
        	   if (line.contains("Phases")) {
        		   DaysInPhase = part2;
        		   System.out.println(DaysInPhase);
                }
        	   if (line.contains("Price")) {
        		   Price = part2;
        		   System.out.println(Price);
                }
        	   if (line.contains("TrellisCrop")) {
        		   Raised = part2;
        		   System.out.println(Raised);
                }
        	   if (line.contains("RegrowthPhase")) {
        		   Regrow = part2;
        		   System.out.println(Regrow);
                }
        	   if (line.contains("HarvestWithScythe")) {
        		   HarvestMethod = part2;
        		   System.out.println(HarvestMethod);
                }
        	   if (line.contains("MinimumPerHarvest")) {
        		   HarvestMinStack = part2;
        		   System.out.println(HarvestMinStack);
                }
        	   if (line.contains("MaximumPerHarvest")) {
        		   HarvestMaxStack = part2;
        		   System.out.println(HarvestMaxStack);
                }
        		}
                }
        	}
            String saplingPurchasePrice = "6000";
            String contextTags = contextTagsBuilder.toString();
            int Seedprice = Integer.valueOf(Price)*2/3;
            int regrowDays = -1;
           if(Regrow!="") { 
        	   regrowDays = Integer.valueOf(Regrow);
           }
           
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.print(""); 
            }

            String jsonOutput = ""+
            		"{\n" +
                    "\t\"Changes\": [\n" +
                    "\t\t{\n" +
                    "\t\t\t\"Action\": \"EditData\",\n" +
                   "\t\t\t\"Target\": \"Data/Crops\",\n" +
                    "\t\t\t\"Entries\": {\n" +
                    "\t\t\t\t\"zzzCustom.crops_" + Name + "\": {\n" +
                    "\t\t\t\t\t\"Seasons\": " + Seasons + ",\n" +
                    "\t\t\t\t\t\"DaysInPhase\": " + DaysInPhase + ",\n" +
                    "\t\t\t\t\t\"HarvestItemId\": \"zzzCustom.crops_"+HarvestItemId+"\",\n" +
                    "\t\t\t\t\t\"RegrowDays\": "+regrowDays+",\n" +
            		"\t\t\t\t\t\"IsRaised\": "+Raised+",\n";
        
            if(!HarvestMethod.contains("false")) {
            	jsonOutput = jsonOutput + "\t\t\t\t\t\"HarvestMethod\": "+HarvestMethod+",\n";
            }
            jsonOutput = jsonOutput +
                    "\t\t\t\t\t\"HarvestMinStack\": "+HarvestMinStack+",\n" +
                    "\t\t\t\t\t\"HarvestMaxStack\": "+HarvestMaxStack+",\n" +
                    "\t\t\t\t\t\"Texture\": \"{{InternalAssetKey: assets/Crops.png}}\",\n" +
                    "\t\t\t\t\t\"SpriteIndex\": 0,\n" +
                    "\t\t\t\t},\n" +
                    "\t\t\t\t\"zzzCustom.crops_" + Name + "\": {\n" +
                    "\t\t\t\t\t\"Name\": \"zzzCustom.crops_" + Name + "\",\n" +
                    "\t\t\t\t\t\"DisplayName\": \"" + DisplayName + "\",\n" +
                   "\t\t\t\t\t\"Type\": \"Seeds\",\n" +
                    "\t\t\t\t\t\"Category\": -74,\n" +
                  "\t\t\t\t\t\"Edibility\": -300,\n" +
                  "\t\t\t\t\t\"Price\": " + Seedprice + ",\n" +
                  "\t\t\t\t\t\"Description\": \"" + Description + "\",\n" +
                    "\t\t\t\t\t\"Texture\": \"{{InternalAssetKey: assets/Objects.png}}\",\n" +
                    "\t\t\t\t\t\"SpriteIndex\": 0,\n" +
                   "\t\t},\n" ;
                    
            // Write the reformatted data back to the file
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.print(jsonOutput);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
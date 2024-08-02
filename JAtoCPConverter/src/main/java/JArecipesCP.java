import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JArecipesCP {

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
        	String EdibleIsDrink = "";
        	StringBuilder recipeBuilder = new StringBuilder();
        	StringBuilder contextTagsBuilder = new StringBuilder();
        	while (scanner.hasNextLine()) {
        	    line = scanner.nextLine().trim().replaceAll("\\s+", " ");
        	    
        	    if (line.contains("Ingredients")) {
                    recipeBuilder.append(line.substring(line.indexOf(":") + 1));
                    while (scanner.hasNextLine()) {
                        line = scanner.nextLine().trim();
                        recipeBuilder.append(line);
                        if (line.contains("]")) {
                            break;
                        }
                    }
        	    } else  if (line.contains("ContextTags")) {
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
        	   if (line.contains("EdibleIsDrink")) {
        		   EdibleIsDrink = part2;
        		   System.out.println(EdibleIsDrink);
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
            String ingredients = recipeBuilder.toString();
            String ingredientsReformatted = reformatString(ingredients);

            try (PrintWriter writer = new PrintWriter(file)) {
                writer.print(""); 
            }

            String jsonOutput = ""+

                    "\t\t\t\t\"zzzCustom.recipes_" + Name + "\": {\n" +
                    "\t\t\t\t\t\"Name\": \"zzzCustom.recipes_" + Name + "\",\n" +
                    "\t\t\t\t\t\"DisplayName\": \"" + DisplayName + "\",\n" +
                    "\t\t\t\t\t\"Description\": \"" + Description + "\",\n" +
                    "\t\t\t\t\t\"Type\": \""+Category+"\",\n" +
                    "\t\t\t\t\t\"Category\": -75,\n" +
                    "\t\t\t\t\t\"Edibility\": "+Edibility+",\n" +
                    "\t\t\t\t\t\"IsDrink\": "+EdibleIsDrink+",\n" +
                    "\t\t\t\t\t\"Price\": " + Price + ",\n" +
                    "\t\t\t\t\t\"Texture\": \"{{InternalAssetKey: assets/food.png}}\",\n" +
                    "\t\t\t\t\t\"SpriteIndex\": 0,\n" +
                    "\t\t\t\t\t\"ContextTags\": [" + contextTags + "],\n" +
                    "\t\t\t\t},\n" +
                    "\t\t\t\t\n" +
                    "\t\t\t\t\n" +
            "\t\"zzzCustom.recipes_" + Name + "\": \""+ ingredientsReformatted + "/10 10/zzzCustom.recipes_"+Name+"/none/"+DisplayName+"\",";
                    
            // Write the reformatted data back to the file
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.print(jsonOutput);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static String reformatString(String input) {
        input = input.replaceAll("\\s+", "");
    	System.out.println("input string with ingredients: "+input);
        StringBuilder result = new StringBuilder();
        Pattern pattern = Pattern.compile("\\{\"Object\":\"?(.*?)\"?,\"Count\":(\\d+)}");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String object = matcher.group(1);
            String count = matcher.group(2);

            result.append(object).append(" ").append(count).append(" ");
        }

        return result.toString().trim();
    }
}
package securityservices.core.component.equipment.appservices;

import securityservices.infrastructure.filesystemadapters.FileManager;
import securityservices.shared.responses.ResultRequest;

public class UseCaseEquipmentController {
    private FileManager fileManager = new FileManager();
    
    public ResultRequest<String> getJsonResource(){        
        ResultRequest<String> dataEquipmentFile = fileManager.read("C:\\Users\\david\\Desktop\\M3\\Jsons\\equipment.json");
        if (dataEquipmentFile.failed()==false) {
            return dataEquipmentFile;
        } 
        return ResultRequest.fails("Not Found");
    }
    
    public ResultRequest<String> getXmlResource(){
        ResultRequest<String> dataEquipmentFile = fileManager.read("C:\\Users\\david\\Desktop\\M3\\Jsons\\equipment.xml");
        if (dataEquipmentFile.failed()==false) {
            return dataEquipmentFile;
        } 
        return ResultRequest.fails("Not Found");
    }  
}

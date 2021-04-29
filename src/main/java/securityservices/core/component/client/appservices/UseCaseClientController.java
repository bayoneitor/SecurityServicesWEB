package securityservices.core.component.client.appservices;

import securityservices.infrastructure.filesystemadapters.FileManager;
import securityservices.shared.responses.ResultRequest;

public class UseCaseClientController {
    private FileManager fileManager = new FileManager();
    
    public ResultRequest<String> getJsonResource(){        
        ResultRequest<String> dataClientFile = fileManager.read("C:\\Users\\david\\Desktop\\M3\\Jsons\\client.json");
        if (dataClientFile.failed()==false) {
            return dataClientFile;
        } 
        return ResultRequest.fails("Not Found");
    }
    
    public ResultRequest<String> getXmlResource(){
        ResultRequest<String> dataClientFile = fileManager.read("C:\\Users\\david\\Desktop\\M3\\Jsons\\client.xml");
        if (dataClientFile.failed()==false) {
            return dataClientFile;
        } 
        return ResultRequest.fails("Not Found");
    }  
}

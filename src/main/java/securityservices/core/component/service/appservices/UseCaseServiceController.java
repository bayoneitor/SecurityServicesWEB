package securityservices.core.component.service.appservices;

import securityservices.infrastructure.filesystemadapters.FileManager;
import securityservices.shared.responses.ResultRequest;

public class UseCaseServiceController {
    private FileManager fileManager = new FileManager();
    
    public ResultRequest<String> getJsonResource(){        
        ResultRequest<String> dataServiceFile = fileManager.read("C:\\Users\\david\\Desktop\\M3\\Jsons\\service.json");
        if (dataServiceFile.failed()==false) {
            return dataServiceFile;
        } 
        return ResultRequest.fails("Not Found");
    }
    
    public ResultRequest<String> getXmlResource(){
        ResultRequest<String> dataServiceFile = fileManager.read("C:\\Users\\david\\Desktop\\M3\\Jsons\\service.xml");
        if (dataServiceFile.failed()==false) {
            return dataServiceFile;
        } 
        return ResultRequest.fails("Not Found");
    }  
}

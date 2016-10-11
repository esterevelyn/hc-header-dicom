package processor;

import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.util.TagUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import processor.db.DataObjectRepository;
import processor.entity.DataObject;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;

@Component
public class Processor {

    @Autowired
    private DataObjectRepository repository;
    private DataObjectRepository repositoryP;
    private DataObjectRepository repositoryS;


    @PostConstruct
    public void init() {
        System.out.println("==================================================================");
        System.out.println("Processador de Imagens Dicom para uma estrutura de banco de dados.");
        System.out.println("==================================================================");


        DicomObject dObject = null;

        try {
            DicomInputStream dis = new DicomInputStream
                    (              new File("C:\\Users\\Ester\\Documents\\dicom\\DICOM\\S00001\\SER00001\\I00003"));
            dObject = dis.readDicomObject();
            dis.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        //Processor list = new Processor();
        //list.listHeader(dObject);
   // }

   // public void listHeader(DicomObject dObject) {
        List<DataObject> dataObjects = new ArrayList<>();
        Iterator<DicomElement> iter = dObject.datasetIterator();
        DataObject object = new DataObject();

        while (iter.hasNext()) {
            DicomElement element = iter.next();
            int tag = element.tag();
            try {
                String tagName = dObject.nameOf(tag);
                String tagAddr = TagUtils.toString(tag);
                String tagValue;
                String tagVR = dObject.vrOf(tag).toString();
                if (!(tagVR.equals("SQ"))) {
                    tagValue = dObject.getString(tag);

                    switch (tagAddr) {
                        case "(0010,0010)":
                            System.out.println(tagName + " = " + tagValue);
                            //object.insertData(tagName, tagValue);
                            object.setNamePatient(tagValue);
                            break;
                        case "(0010,0020)":

                            System.out.println(tagName + " = " + tagValue);
                            //object.insertData(tagName, tagValue);
                            object.setIdPatient(tagValue);
                            break;
                        case "(0008,0020)":
                            System.out.println(tagName + " = " + tagValue);
                            object.insertData(tagName, tagValue);

                            break;
                        case "(0008,0030)":
                            System.out.println(tagName + " = " + tagValue);
                            object.insertData(tagName, tagValue);
                            break;
                        case "(0010,0040)":

                            System.out.println(tagName + " = " + tagValue);
                            //object.insertData(tagName, tagValue);
                            object.setSexPatient(tagValue);
                            break;
                        case "(0010,0030)":
                            System.out.println(tagName + " = " + tagValue);
                            //object.insertData(tagName, tagValue);
                            object.setBirthDatePatient(tagValue);
                            break;
                        case "(0018,1030)":
                            System.out.println(tagName + " = " + tagValue);
                            object.insertData(tagName, tagValue);
                            break;
                        case "(0008,1030)":
                            System.out.println(tagName + " = " + tagValue);
                            object.insertData(tagName, tagValue);
                            break;
                        case "(0020,0010)":
                            System.out.println(tagName + " = " + tagValue);
                            object.insertData(tagName, tagValue);
                            break;
                        case "(0010,1010)":
                            System.out.println(tagName + " = " + tagValue);
                            //object.insertData(tagName, tagValue);
                            object.setAgePatient(tagValue);
                            break;
                        case "(0020,000D)":
                            System.out.println(tagName + " = " + tagValue);
                            object.insertData(tagName, tagValue);
                            break;
                        case "(00E1,1021)":
                            System.out.println("DLP" + " = " + tagValue);
                            object.insertData("DLP", tagValue);
                        default:
                            break;
                    }
                   // System.out.println(tagAddr + " [" + tagVR + "] " + tagName + " [" + tagValue + "]");
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        dataObjects.add(object);


       for (DataObject data : dataObjects) {
           repository.save(data);
       }
        //System.out.println(repository.findByIdPatient(""));
        //for (DataObject customer : repository.findAll()) {
           System.out.println(repository.findByIdPatient("1230310K"));
       // }
    }
}

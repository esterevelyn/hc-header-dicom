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
    //private ProcessorRepository repository;

    @PostConstruct
    public void init() {
        System.out.println("==========================================================");
        System.out.println("Processador de texto para uma estrutura de banco de dados.");
        System.out.println("==========================================================");

        //String filename = "data.txt";


        DicomObject dObject = null;

        try {
            DicomInputStream dis = new DicomInputStream
                    (new File("C:\\Users\\Ester\\Documents\\dicom\\DICOM\\S00001\\SER00001\\I00003"));
            dObject = dis.readDicomObject();
            dis.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        Processor list = new Processor();
        list.listHeader(dObject);

       // try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

        // DataObject object = new DataObject();

           // String line;
           // while ((line = br.readLine()) != null) {
                // Se for uma linha vazia, guarda o objeto populado e prepara um novo
              //  if (line.isEmpty()) {
                    //dataObjects.add(object);
                  //  object = new DataObject();
              //  } else { // Se é linha de dados, preenche no objeto atual
                    //String[] lineKeyValue = line.split("\t");
                   // object.insertData(lineKeyValue[0], lineKeyValue[1]);
             // }
          //  }

            // Essa linha imprimiria todos os objetos processados
            // dataObjects.forEach(System.out::println);

            //System.out.println("Total de objetos processados e estruturados: " + dataObjects.size());

            // Salva todos os objetos no banco
            //for (DataObject data : dataObjects) {
               // repository.save(data);
           // }

       // } catch (IOException e) {
          //  System.out.println("Nome de arquivo de entrada inválido.");
          //  e.printStackTrace();
       // }
    }

    public void listHeader(DicomObject dObject) {
       List<DataObject> dataObjects = new ArrayList<>();
        // Stack pilha = new Stack();
        //variaveis
        //lala
        //que foda
        ArrayList patient = new ArrayList();
        ArrayList study = new ArrayList();
        ArrayList tPatient = new ArrayList();
        Map<String, String> dataPatient = new HashMap<>();
        Map<String, String> dataStudy = new HashMap<>();
        Iterator<DicomElement> iter = dObject.datasetIterator();
        DataObject object = new DataObject();

        //enquanto
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
                    //dataObjects.add(object);
                    switch (tagAddr) {
                        case "(0010,0010)":
                            System.out.println(tagName + " = " + tagValue);
                            dataPatient.put(tagName, tagValue);
                            object.insertData(tagName, tagValue);

                            break;
                        case "(0010,0020)":

                            System.out.println(tagName + " = " + tagValue);
                            dataPatient.put(tagName, tagValue);
                            object.insertData(tagName, tagValue);


                            break;
                        case "(0008,0020)":
                            System.out.println(tagName + " = " + tagValue);
                            //System.out.println(tagAddr+ tagName + " = " + tagValue);
                            dataStudy.put(tagName, tagValue);
                            object.insertData(tagName, tagValue);

                            break;
                        case "(0008,0030)":
                            System.out.println(tagName + " = " + tagValue);
                            dataStudy.put(tagName, tagValue);
                            object.insertData(tagName, tagValue);
                            break;
                        case "(0010,0040)":
                            dataPatient.put(tagName, tagValue);
                            System.out.println(tagName + " = " + tagValue);
                            object.insertData(tagName, tagValue);
                            break;
                        case "(0010,0030)":
                            dataPatient.put(tagName, tagValue);
                            System.out.println(tagName + " = " + tagValue);
                            object.insertData(tagName, tagValue);
                            break;
                        case "(0018,1030)":
                            dataStudy.put(tagName, tagValue);
                            System.out.println(tagName + " = " + tagValue);
                            object.insertData(tagName, tagValue);
                            break;
                        case "(0008,1030)":
                            dataStudy.put(tagName, tagValue);
                            System.out.println(tagName + " = " + tagValue);
                            object.insertData(tagName, tagValue);
                            break;
                        case "(0020,0010)":
                            dataStudy.put(tagName, tagValue);
                            System.out.println(tagName + " = " + tagValue);
                            object.insertData(tagName, tagValue);
                            break;
                        case "(0010,1010)":
                            dataPatient.put(tagName, tagValue);
                            System.out.println(tagName + " = " + tagValue);
                            object.insertData(tagName, tagValue);
                            break;
                        case "(0020,000D)":
                            dataStudy.put(tagName, tagValue);
                            System.out.println(tagName + " = " + tagValue);
                            object.insertData(tagName, tagValue);
                            break;
                        case "(00E1,1021)":
                            dataStudy.put("DLP", tagValue);
                            System.out.println("DLP" + " = " + tagValue);
                            object.insertData("DLP", tagValue);
                        default:
                            break;
                    }
                   // System.out.println(tagAddr + " [" + tagVR + "] " + tagName + " [" + tagValue + "]");
                }
               // dataObjects.forEach(System.out::println);


            } catch (Exception e) {
                e.printStackTrace();

            }
            //dataObjects.add(object);
        }
       // object.Nam("DLP");
       // dataObjects.add(object);
        //object = new DataObject();
        dataObjects.add(object);
        object.get();
        patient.add(dataPatient);
        study.add(dataStudy);
        patient.add(study);
        tPatient.add(patient);
        //System.out.print("LISTA = ");
       // tPatient.forEach(System.out::println);
        //System.out.print(object.getId());
        //Essa linha imprimiria todos os objetos processados
        //System.out.println();
        //dataObjects.forEach(System.out::println);
        //dataObjects.get(1);
       //for (DataObject data : dataObjects) {
           //repository.save(object);
           //System.out.println(data);
       // }
       // dataObjects.
        // System.out.println(tPatient.size());

    }
}

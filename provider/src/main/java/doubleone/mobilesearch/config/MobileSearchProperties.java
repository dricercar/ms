package doubleone.mobilesearch.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("mobilesearch")
@Configuration
public class MobileSearchProperties{

    private Scratch scratch = new Scratch();
    
    public static class Scratch{
        private String host="127.0.0.1";
        private List<String> processors = new ArrayList<>();
        private String dataDir = "data";

        public List<String> getProcessors() {
            return processors;
        }
        public void setProcessors(List<String> processors) {
            this.processors = processors;
        }
        public String getHost() {
            return this.host;
        }
        public void setHost(String host) {
            this.host = host;
        }
        public String getDataDir() {
            return dataDir;
        }
        public void setDataDir(String dataDir) {
            this.dataDir = dataDir;
        }
    }

    
    public Scratch getScratch(){
        return scratch;
    }
    public void setScratch(Scratch scratch){
        this.scratch = scratch;
    }
}
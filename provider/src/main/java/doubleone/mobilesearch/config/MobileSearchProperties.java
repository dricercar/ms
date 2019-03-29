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
    private Elastic elastic = new Elastic();

    public static class Scratch {
        private String host = "127.0.0.1";
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

    public static class Elastic {
        private String host = "127.0.0.1";
        private int port = 9200;
        private String index = "mobileSearch";

        /**
         * @return the host
         */
        public String getHost() {
            return host;
        }

        /**
         * @return the index
         */
        public String getIndex() {
            return index;
        }

        /**
         * @param index the index to set
         */
        public void setIndex(String index) {
            this.index = index;
        }

        /**
         * @return the port
         */
        public int getPort() {
            return port;
        }

        /**
         * @param port the port to set
         */
        public void setPort(int port) {
            this.port = port;
        }

        /**
         * @param host the host to set
         */
        public void setHost(String host) {
            this.host = host;
        }

    }

    public Scratch getScratch() {
        return scratch;
    }

    /**
     * @return the elastic
     */
    public Elastic getElastic() {
        return elastic;
    }

    /**
     * @param elastic the elastic to set
     */
    public void setElastic(Elastic elastic) {
        this.elastic = elastic;
    }

    public void setScratch(Scratch scratch) {
        this.scratch = scratch;
    }
}
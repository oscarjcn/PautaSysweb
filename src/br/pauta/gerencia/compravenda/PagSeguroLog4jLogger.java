package br.pauta.gerencia.compravenda;
  
import br.com.uol.pagseguro.logs.Logger;  
  
public class PagSeguroLog4jLogger implements Logger {  
  
    private org.apache.log4j.Logger loggerImpl;  
  
    public PagSeguroLog4jLogger(Class clazz) {  
        loggerImpl = org.apache.log4j.Logger.getLogger(clazz);  
    }  
  
    public void info(String message) {  
        loggerImpl.info(message);  
    }  
  
    public void debug(String message) {  
        loggerImpl.debug(message);  
    }  
  
    public void warn(String message) {  
        loggerImpl.warn(message);  
    }  
  
    public void error(String message) {  
        loggerImpl.error(message);  
    }  
  
    public void warn(String message, Throwable t) {  
        loggerImpl.warn(message, t);  
    }  
  
    public void error(String message, Throwable t) {  
        loggerImpl.error(message, t);  
    }  
}  
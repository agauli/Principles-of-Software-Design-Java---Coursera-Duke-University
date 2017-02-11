import java.util.*;
import edu.duke.*;

public class EarthQuakeClient {
    public EarthQuakeClient() {
        
    }

    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
    double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        for (QuakeEntry que : quakeData){
            if ( que.getMagnitude() > magMin){
                answer.add(que);
            }
        }
        return answer;
    }

    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData,
    double distMax, Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        for (QuakeEntry que: quakeData){
           double distance = (double) from.distanceTo(que.getLocation());
           if (distance < distMax)
         // if (que.getLocation().distanceTo(from) < distMax)
          { answer.add(que);}
        }
        return answer;
    }
    
    
    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData,
    double minDepth, double maxDepth) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        System.out.println("depth");
        for (QuakeEntry que : quakeData){
            if (( que.getDepth() > maxDepth) && ( que.getDepth() < minDepth)){
                answer.add(que);
            }
        }
        return answer;
    }
    
    
    
    public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry> quakeData,
    String where, String phrase) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        for (QuakeEntry que : quakeData){
           
            if (where.equals("start")){ 
                if(que.getInfo().matches(phrase+"(.*)")) answer.add(que);}
                
            if (where.equals("end")){ 
                if(que.getInfo().matches("(.*)"+ phrase)) answer.add(que);}
            
            if (where.equals("any")){ 
                if(que.getInfo().matches("(.*)" + phrase+"(.*)")) answer.add(que);}    
            
        }
        return answer;
    }
   

    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }

    }

    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);     
        System.out.println("read data for " + list.size()+" quakes");
        ArrayList <QuakeEntry > bigquake = filterByMagnitude(list, 5.0); 
        for (QuakeEntry big : bigquake){
            System.out.println(big);
        }

    }

    public void closeToMe(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" new quakes");
        
        // This location is Durham, NC
        //Location city = new Location(35.988, -78.907);

        // This location is Bridgeport, CA
        Location city =  new Location(38.17, -118.82);
        ArrayList<QuakeEntry> nearest =  filterByDistanceFrom(list, 1000 *1000, city);
        for (QuakeEntry nearquake : nearest){
            double distance = city.distanceTo(nearquake.getLocation());
            System.out.println("" + distance/1000 + nearquake);
        }  
        // TODO
    }
    
    
    
    public void quakesOfDepth() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);  
        System.out.println("read data for " + list.size()+" quakes");
        ArrayList <QuakeEntry > bigquake = filterByDepth(list, -5000, -10000); 
        for (QuakeEntry big : bigquake){
            System.out.println(big);
        }

    }

    public void quakesByPhrase() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);  
        System.out.println("read data for " + list.size()+" quakes");
        ArrayList <QuakeEntry > phrasequake =  filterByPhrase(list, "start", "Explosion"  );
        
        for (QuakeEntry phrased : phrasequake){

            System.out.println(phrased);
        }
    }
   

    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
    }
}

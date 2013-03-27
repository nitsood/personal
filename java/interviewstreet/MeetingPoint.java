package interviewstreet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MeetingPoint
{
  public static void main(String args[])
  {
    List<Point> points = new ArrayList<Point>();
    Scanner sc = new Scanner(System.in);
    int sz = Integer.parseInt(sc.nextLine());
    
    //Random r = new Random();
    for(int i=0; i<sz; i++)
    {
      //String co-ords
      long x = Long.parseLong(sc.next());
      long y = Long.parseLong(sc.next());
      points.add(new Point(x, y));
    }
    
    /*points.add(new Point(12, -14));
    points.add(new Point(-3, 3));
    points.add(new Point(-14, 7));
    points.add(new Point(-14, -3));
    points.add(new Point(2, -12));
    points.add(new Point(-1, -6));
    */
    
    Point centroid = Point.getCentroid(points);
    //System.out.println("The centroid is: "+centroid.toString());
    Point nearest = Point.findNearestToCentroidPoint(points, centroid);
    //System.out.println("The nearest is: "+nearest.toString());
    
    System.out.println(getTravelTimes(nearest, points));
    
    /*Point source = new Point(1000000000L, 1000000000L);
    Point dest = new Point(5000000000L, 6000000000L);
    Direction d = Direction.findDirection(source, dest);
    System.out.println("The direction is "+d);
    long dist;
      
    if(d == null)
      dist = Direction.getLateralDistance(source, dest);
    else
      dist = d.getDistanceBetween(source, dest);
    
    System.out.println("The distance is "+dist);*/
  }
  
  private static long getTravelTimes(Point source, Collection<Point> points)
  {
    long sum = 0;
    //Point meetingPoint = new Point(source.getX(), source.getY());
    for(Point dest : points)
    {
      Direction d = Direction.findDirection(source, dest);
      long dist = 0;
      
      if(d == null)
        dist = Direction.getLateralDistance(source, dest);
      else
        dist = d.getDistanceBetween(source, dest);
      //System.out.println("The dist between "+source.toString()+" and "+dest.toString()+" is "+dist);
      sum+=dist;
      //source = meetingPoint;
    }
    
    return sum;
  }
  
  public enum Direction
  {
    NORTHEAST
    {
      @Override
      public long getDistanceBetween(Point source, Point dest)
      {
        long offset = Direction.getOffset(source, dest);
     
        Point temp = new Point(source.getX()+offset, source.getY()+offset);
        return offset+getLateralDistance(temp, dest);
      }
    },
    NORTHWEST
    {
      @Override
      public long getDistanceBetween(Point source, Point dest)
      {
        long offset = Direction.getOffset(source, dest);
     
        Point temp = new Point(source.getX()-offset, source.getY()+offset);
        return offset+getLateralDistance(temp, dest);
      }
    },
    SOUTHEAST
    {
      @Override
      public long getDistanceBetween(Point source, Point dest)
      {
        long offset = Direction.getOffset(source, dest);
     
        Point temp = new Point(source.getX()+offset, source.getY()-offset);
        return offset+getLateralDistance(temp, dest);
      }
    },
    SOUTHWEST
    {
      @Override
      public long getDistanceBetween(Point source, Point dest)
      {
        long offset = Direction.getOffset(source, dest);
     
        Point temp = new Point(source.getX()-offset, source.getY()-offset);
        return offset+getLateralDistance(temp, dest);
      }
    }
    ;
    
    public abstract long getDistanceBetween(Point source, Point dest);
    
    public static Direction findDirection(Point source, Point dest)
    {
      if(source.equals(dest))
        return null;
      
      long p = source.getX();
      long q = source.getY();
      long r = dest.getX();
      long s = dest.getY();
      
      if(p<r && q>s)
        return Direction.SOUTHEAST;
      else if(p<r && q<s)
        return Direction.NORTHEAST;
      else if(p>r && q>s)
        return Direction.SOUTHWEST;
      else if(p>r && q<s)
        return Direction.NORTHWEST;
      else return null;
    }
    
    public static long getLateralDistance(Point source, Point dest)
    {
      if(source.equals(dest))
        return 0;
      
      long p = source.getX();
      long q = source.getY();
      long r = dest.getX();
      long s = dest.getY();
      
      if(p == r)
        return Math.abs(s-q);
      else if(q == s)
        return Math.abs(r-p);
      else
        throw new RuntimeException("Cannot happen");
    }
    
    public static long getOffset(Point source, Point dest)
    {
      long p = source.getX();
      long q = source.getY();
      long r = dest.getX();
      long s = dest.getY();
      long absX = Math.abs(r-p);
      long absY = Math.abs(s-q);
      
      return Math.min(absX, absY);
    }
  }
  
  public static class Point
  {
    private long x;
    private long y;
    
    //for hashcode implementation
    private static final int SEED = 42;
    private static final int fODD_PRIME_NUMBER = 37;
    
    public Point(long x, long y)
    {
      this.x = x;
      this.y = y;
    }
    
    public long getX()
    {
      return x;
    }
    
    public long getY()
    {
      return y;
    }
    
    public void setX(long x)
    {
      this.x = x;
    }
    
    public void setY(long y)
    {
      this.y = y;
    }
    
    public String toString()
    {
      return ("("+getX()+", "+getY()+")");
    }
    
    @Override
    public int hashCode()
    {
      int result = SEED;
      
      hash(result, x);
      hash(result, y);
      
      return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
      if(obj != null && obj instanceof Point)
      {
        Point coordinate = (Point) obj;
        
        if(this.getX() == coordinate.getX() &&
           this.getY() == coordinate.getY())
        {
          return true;
        }
      }
      
      return false;
    }
    
    private long hash(long aSeed, long aInt)
    {
      return (fODD_PRIME_NUMBER * aSeed) + aInt;
    }
    
    public static long getEuclideanDistance(Point source, Point dest)
    {
      long p = source.getX();
      long q = source.getY();
      long r = dest.getX();
      long s = dest.getY();
      
      double diffX = (double) Math.abs(r-p);
      double diffY = (double) Math.abs(s-q);
      
      return (long) Math.sqrt(diffX*diffX + diffY*diffY);
    }
    
    public static Point getCentroid(Collection<Point> points)
    {
      long centX = 0;
      long centY = 0;
      for(Point p : points)
      {
        centX+=p.getX();
        centY+=p.getY();
      }
      
      centX=centX/points.size();
      centY=centY/points.size();
      return new Point(centX, centY);
    }
    
    public static Point findNearestToCentroidPoint(Collection<Point> points, Point centroid)
    {
      long mindist = Long.MAX_VALUE;
      Point nearest = null;
      
      for(Point p : points)
      {
        long d = Point.getEuclideanDistance(p, centroid);
        if(d < mindist)
        {
          mindist = d;
          nearest = p;
        }
      }
      
      return nearest;
    }
  }
}  
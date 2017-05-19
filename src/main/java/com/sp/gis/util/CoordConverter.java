package com.sp.gis.util;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

/**
 * Created by m on 2017/5/19.
 */
public class CoordConverter {
    public static double[] convert(double lon, double lat)
            throws FactoryException, MismatchedDimensionException, TransformException {
        // 传入原始的经纬度坐标
        Coordinate sourceCoord = new Coordinate(lon, lat);
        GeometryFactory geoFactory = new GeometryFactory();
        Point sourcePoint = geoFactory.createPoint(sourceCoord);

        // 这里是以OGC WKT形式定义的是World Mercator投影，网页地图一般使用该投影
        final String strWKTMercator = "PROJCS[\"Popular Visualisation CRS / Mercator\",GEOGCS[\"Popular Visualisation CRS\",DATUM[\"Popular_Visualisation_Datum\",SPHEROID[\"Popular Visualisation Sphere\",6378137,0,AUTHORITY[\"EPSG\",\"7059\"]],TOWGS84[0,0,0,0,0,0,0],AUTHORITY[\"EPSG\",\"6055\"]],PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],UNIT[\"degree\",0.01745329251994328,AUTHORITY[\"EPSG\",\"9122\"]],AUTHORITY[\"EPSG\",\"4055\"]],UNIT[\"metre\",1,AUTHORITY[\"EPSG\",\"9001\"]],PROJECTION[\"Mercator_1SP\"],PARAMETER[\"central_meridian\",0],PARAMETER[\"scale_factor\",1],PARAMETER[\"false_easting\",0],PARAMETER[\"false_northing\",0],AUTHORITY[\"EPSG\",\"3785\"],AXIS[\"X\",EAST],AXIS[\"Y\",NORTH]]";
        CoordinateReferenceSystem mercatroCRS = CRS.parseWKT(strWKTMercator);
        // 做投影转换，将WCG84坐标转换成世界墨卡托投影转
        MathTransform transform = CRS.findMathTransform(DefaultGeographicCRS.WGS84, mercatroCRS);
        Point targetPoint = (Point) JTS.transform(sourcePoint, transform);

        // 返回转换以后的X和Y坐标
        double[] targetCoord = {targetPoint.getX(), targetPoint.getY()};
        return targetCoord;
    }

    // 将目标投影坐标系作为参数输入，其实和第一个程序类似，我懒得提取公共部分再抽取函数了
    public static double[] convert(double lon, double lat, String strWKT)
            throws FactoryException, MismatchedDimensionException, TransformException {
        Coordinate sourceCoord = new Coordinate(lon, lat);
        GeometryFactory geoFactory = new GeometryFactory();
        Point sourcePoint = geoFactory.createPoint(sourceCoord);

        CoordinateReferenceSystem mercatroCRS = CRS.parseWKT(strWKT);
        MathTransform transform = CRS.findMathTransform(DefaultGeographicCRS.WGS84, mercatroCRS);
        Point targetPoint = (Point) JTS.transform(sourcePoint, transform);

        double[] targetCoord = {targetPoint.getX(), targetPoint.getY()};
        return targetCoord;
    }

    public static double[] convertByWKT(double lon, double lat, String srcWKT, String desWKT)
            throws FactoryException, MismatchedDimensionException, TransformException {
        Coordinate sourceCoord = new Coordinate(lon, lat);
        GeometryFactory geoFactory = new GeometryFactory();
        Point sourcePoint = geoFactory.createPoint(sourceCoord);

        CoordinateReferenceSystem srcCRS = CRS.parseWKT(srcWKT);
        CoordinateReferenceSystem destCRS = CRS.parseWKT(desWKT);




        MathTransform transform = CRS.findMathTransform(srcCRS, destCRS,true);
        Point targetPoint = (Point) JTS.transform(sourcePoint, transform);

        double[] targetCoord = {targetPoint.getX(), targetPoint.getY()};
        return targetCoord;
    }

    public static double[] convertByEPSG(double lon, double lat, String srcEPSG, String desEPSG)
            throws FactoryException, MismatchedDimensionException, TransformException {
        Coordinate sourceCoord = new Coordinate(lon, lat);
        GeometryFactory geoFactory = new GeometryFactory();
        Point sourcePoint = geoFactory.createPoint(sourceCoord);

        CoordinateReferenceSystem srcCRS = CRS.decode(srcEPSG);
        CoordinateReferenceSystem destCRS = CRS.decode(desEPSG);




        MathTransform transform = CRS.findMathTransform(srcCRS, destCRS,true);
        Point targetPoint = (Point) JTS.transform(sourcePoint, transform);

        double[] targetCoord = {targetPoint.getX(), targetPoint.getY()};
        return targetCoord;
    }

    public static double[] reConvert(double x, double y, String strWKT) throws FactoryException, TransformException {
        Coordinate sourceCoord = new Coordinate(x, y);
        GeometryFactory geoFactory = new GeometryFactory();
        Point sourcePoint = geoFactory.createPoint(sourceCoord);

        CoordinateReferenceSystem mercatroCRS = CRS.parseWKT(strWKT);
        MathTransform transform = CRS.findMathTransform(mercatroCRS, DefaultGeographicCRS.WGS84);
        Point targetPoint = (Point) JTS.transform(sourcePoint, transform);

        double[] targetCoord = {targetPoint.getX(), targetPoint.getY()};
        return targetCoord;
    }

    // main函数进行验证
    public static void main(String[] args) throws Exception {
        final String strWKTMercator = "PROJCS[\"Popular Visualisation CRS / Mercator\",GEOGCS[\"Popular Visualisation CRS\",DATUM[\"Popular_Visualisation_Datum\",SPHEROID[\"Popular Visualisation Sphere\",6378137,0,AUTHORITY[\"EPSG\",\"7059\"]],TOWGS84[0,0,0,0,0,0,0],AUTHORITY[\"EPSG\",\"6055\"]],PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],UNIT[\"degree\",0.01745329251994328,AUTHORITY[\"EPSG\",\"9122\"]],AUTHORITY[\"EPSG\",\"4055\"]],UNIT[\"metre\",1,AUTHORITY[\"EPSG\",\"9001\"]],PROJECTION[\"Mercator_1SP\"],PARAMETER[\"central_meridian\",0],PARAMETER[\"scale_factor\",1],PARAMETER[\"false_easting\",0],PARAMETER[\"false_northing\",0],AUTHORITY[\"EPSG\",\"3785\"],AXIS[\"X\",EAST],AXIS[\"Y\",NORTH]]";
        final String strWGS84="GEOGCS[\"WGS 84\",DATUM[\"WGS_1984\",SPHEROID[\"WGS 84\",6378137,298.257223563,AUTHORITY[\"EPSG\",\"7030\"]],AUTHORITY[\"EPSG\",\"6326\"]],PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],UNIT[\"degree\",0.01745329251994328,AUTHORITY[\"EPSG\",\"9122\"]],AUTHORITY[\"EPSG\",\"4326\"]]";

        String srcEPSG="EPSG:4326";
        String desEPSG="EPSG:3857";
        double longitude = 118.65018035274457;
        double latitude = 32.02654232075092;
        double[] coordinate = convertByEPSG(latitude,longitude,  srcEPSG, desEPSG);

        System.out.println("X: " + coordinate[0] + ", Y: " + coordinate[1]);

        double[] reCoor = convertByEPSG(coordinate[0], coordinate[1], desEPSG, srcEPSG);
        System.out.println("longitude: " + reCoor[0] + ", latitude: " + reCoor[1]);

        double[] rereCoor = convertByEPSG(reCoor[0],reCoor[1],  srcEPSG, desEPSG);
        System.out.println("rereCoor: " + rereCoor[0] + ", rereCoor: " + rereCoor[1]);

    }
}

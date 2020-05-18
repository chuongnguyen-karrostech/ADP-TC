package com.ADP.util.spcs;

public class LatLonSPCSCon
{
  /* These defines are a few of the constants used in the
   * lat long to state plane calculations.
   */
  public static double D2R = 0.0174532925199; /* degrees to radians */
  public static double F2M = 0.3048006096012; /* feet to meters */
  public static double U0 =  -0.005048250776;
  public static double U2 = 0.000021259204;
  public static double U4 = -0.000000111423;
  public static double U6 = 0.000000000626;
  public static double E2 = 0.0066943800229034;
  public static double V0 = 0.005022893948;
  public static double V2 = 0.000029370625;
  public static double V4 = 0.000000235059;
  public static double V6 = 0.000000002181;
  static double ESQ=  0.0066943800229034 ; /* square of first eccentricity */
  static double ER = 6378137.0;	/* semimajor axis */
  static double R = 6367449.14577;
  static double RF = 298.257222101;
  
  public int convert_to_xy(GEODETIC g)
  {
    int retval = 0;

    if (g.coordinates == COORDINATES.LAMBERT) 
    {
      lambert_geo2xy(g);
    }
    else if (g.coordinates == COORDINATES.T_MERCATOR) 
    {
      tm_geo2xy(g);
    }
    else 
    {
      retval = -1;
    }
    
    return retval;
  }

  public int convert_to_geodetic(GEODETIC g)
  {
    int retval = 0;

    if (g.coordinates == COORDINATES.LAMBERT) 
    {
      lambert_xy2geo(g);
    }
    else if (g.coordinates == COORDINATES.T_MERCATOR) 
    {
      tm_xy2geo(g);
    }
    else 
    {
      retval = -1;
    }
    
    return retval;
  }
  
  public static void lambert_geo2xy(GEODETIC g)
  {
    double E;				/* first eccentricity of elipsoid */
    double E0;				/* false easting value	*/
    double K;				/* mapping radius at equator */
    double CM;				/* central meridian */
    double NB;				/* northing val @ central long and lat */
    double RB;				/* mapping radius at latitude of grid origin */
    double SINF0;
    double x_adj;			/* fudge factors */
    double y_adj;

    double FI;				/* geodetic latitude */
    double LAM;				/* geodetic longitude */
    double CONV;			/* convergence angle */
    double EAST;			/* Easting Coordinate  (formerly x) */
    double NORTH;			/* Northing Coordinate (formerly y) */
    double Q;				/* isometric latitude */
    double RPT;				/* mapping radius at geodetic_latitude */
    double SINLAT;

    double partial1, partial2;

    CM = g.cons.lambert.CM;
    NB = g.cons.lambert.NB;
    E0 = g.cons.lambert.E0;
    SINF0 = g.cons.lambert.SINF0;
    RB = g.cons.lambert.RB;
    K = g.cons.lambert.K;
    x_adj = g.cons.lambert.x_adj;
    y_adj = g.cons.lambert.y_adj;
    E =java.lang.Math.sqrt(E2);

    LAM = g.data.lon * D2R;		/* convert to radians */
    FI = g.data.lat * D2R;
    SINLAT =java.lang.Math.sin(FI);
    CONV = (CM - LAM) * SINF0;

    partial1 = java.lang.Math.log( (1 + SINLAT) / (1 - SINLAT) );
    partial2 = java.lang.Math.log( (1 + (E * SINLAT)) / (1 - (E * SINLAT)) );
    Q = (partial1 - (E * partial2)) / 2;
    RPT = K / java.lang.Math.exp(Q * SINF0);

    NORTH = RB + NB - (RPT * java.lang.Math.cos(CONV));
    EAST = E0 + (RPT * java.lang.Math.sin(CONV));

    g.data.x = (meters2feet(EAST) - x_adj);
    g.data.y = (meters2feet(NORTH) - y_adj);

  }

  public static void tm_geo2xy(GEODETIC g)
  {
    double CM;		/* central meridian */
    double FI;		/* latitude to convert */
    double LAM;         /* longitude to convert	*/
    double SINFI;
    double COSFI;
    double TN;		/* tan(FI) */
    double TS;		/* tan(FI) squared */
    double FE;		/* false easting at central meridian	*/
    double FN;		/* false northing a southernmost lat	*/
    double SF;		/* scale factor	*/
    double S0;
    double EPS;
    double x_adj;
    double y_adj;
    double ETS;
    double L;
    double LS;
    double OM;
    double RN;
    double EAST;
    double NORTH;
    double A1, A2, A3, A4, A5, A6, A7;
    double A, B, C;
    double S;

    CM = g.cons.tm.CM;
    FE = g.cons.tm.FE;
    FN = g.cons.tm.FN;
    A  = g.cons.tm.A;
    B  = g.cons.tm.B;
    C  = g.cons.tm.C;
    SF = g.cons.tm.SF;
    S0 = g.cons.tm.S0;
    x_adj = g.cons.tm.x_adj;
    y_adj = g.cons.tm.y_adj;

    FI = g.data.lat * D2R;
    LAM = g.data.lon * D2R;

    OM = FI + A*java.lang.Math.sin(2.0*FI) + B* java.lang.Math.sin(4.0*FI) + C* java.lang.Math.sin(6.0*FI);

    SINFI = java.lang.Math.sin(FI);
    COSFI = java.lang.Math.cos(FI);
    TN = SINFI / COSFI;
    TS = TN * TN;
    EPS = ESQ / (1-ESQ);
    ETS = EPS * java.lang.Math.pow(COSFI,2);
    L = (LAM - CM) * COSFI;
    LS = L * L;

    S = SF * OM * R;
    RN = SF * ER / java.lang.Math.sqrt(1.0 - ESQ * java.lang.Math.pow(SINFI, 2));

    A2 = (RN * TN) / 2.0;
    A4 = (5.0 - TS + ETS * (9.0 + 4.0 * ETS)) / 12.0;
    A6 = (61.0 + TS * (TS - 58.0) + ETS * (270.0 - (330.0 * TS)))/ 360.0;
    NORTH = S - S0 + A2 * LS * (1.0 + LS * (A4 + A6 * LS)) + FN;

    A1 = -RN;
    A3 = (1.0 - TS + ETS) / 6.0;
    A5 = (5.0 + TS*(TS - 18.0) + ETS * (14.0 - (58.0*TS)))/120.0;
    A7 = (61.0 - (479.0 * TS) + (179.0 * java.lang.Math.pow(TS, 2)) - java.lang.Math.pow(TN, 3)) / 5040.0;
    EAST = FE + A1 * L * (1 + LS * (A3 + LS * (A5 + A7 * LS)));

    g.data.y = meters2feet(NORTH) - y_adj;
    g.data.x = meters2feet(EAST) - x_adj;
  }
  
  public static void lambert_xy2geo(GEODETIC g)
  {
    double LAT;		/* latitude */
    double LAM;		/* longitude */
    double NORTH;       /* northing coordinate */
    double EAST;        /* easting coordinate */
    double E;		/* 1st eccentricity */
    double CM;		/* central meridian ofthe zone */
    double E0;		/* false easting at central meridian */
    double NB;		/* false northing of S parallel of zone */
    double SINF0;	/* sin(FO) where FO is central parallel */
    double RB;		/* mapping radius at S parallel of zone	*/
    double K;		/* mapping radius at the equator */
    double EPR;		/* adjusted easting */
    double NPR;		/* adjusted northing */
    double GAM;
    double LON;
    double TMP;
    double F1, F2;
    double RPT;
    double Q;
    double SIN;
    int i;

    CM = g.cons.lambert.CM;
    NB = g.cons.lambert.NB;
    E0 = g.cons.lambert.E0;
    SINF0 = g.cons.lambert.SINF0;
    RB = g.cons.lambert.RB;
    K = g.cons.lambert.K;
    NORTH = feet2meters(g.data.y) + feet2meters(g.cons.lambert.y_adj);
    EAST = feet2meters(g.data.x) + feet2meters(g.cons.lambert.x_adj);

    E = java.lang.Math.sqrt(ESQ);
    NPR = RB - NORTH + NB;
    EPR = EAST - E0;
    GAM = java.lang.Math.atan(EPR / NPR);
    LON = CM - (GAM / SINF0);
    RPT = java.lang.Math.sqrt((NPR * NPR) + (EPR * EPR));
    Q = java.lang.Math.log((K / RPT))/ SINF0;
    TMP = java.lang.Math.exp(Q + Q);
    SIN = (TMP - 1.0)/(TMP + 1.0);

    for (i = 0; i < 3; i++) 
    {
      F1 = (java.lang.Math.log((1.0+SIN)/(1.0-SIN))-E*java.lang.Math.log((1.0+E*SIN) / (1.0-E*SIN)))/2.0 - Q;
      F2 = 1.0/(1.0-SIN*SIN) - ESQ/(1.0-ESQ*SIN*SIN);
      SIN = SIN - (F1 / F2);
    }
    
    LAT = java.lang.Math.asin(SIN);
    LAM = LON;

    g.data.lat =  LAT / D2R;
    g.data.lon =  LAM / D2R;

  }

  public static double meters2feet(double meters)
  {
    return meters / (double) F2M;
  }

  public static double feet2meters(double feet)
  {
    return feet * (double) F2M;
  }

  public static int get_spcs_data(int zone, SPCS_DATA data)
  {
    try
    {
      int retval = -1;
      int i, j;
      int size;

      for (i = 0; i < spcs_dat.ZONE.length; i++)
      {
        if (spcs_dat.ZONE[i] == zone)
        {
          data.zone_code = zone;
          data.type = spcs_dat.TYPE[i];
          data.desc = spcs_dat.DESCR[i];
        
	  for (j = 0; j < 6; j++)
          {
            try
            {
              data.data[j] =(double)spcs_dat.DATA[i][j];
            }
            catch(Exception e)
	    {
              e.printStackTrace(System.out);
            }
          }
	  
          retval = 0;
          break;
        } 
      }  
      
      return retval;
    }
    catch(Exception e)
    {
      e.printStackTrace();
    
      return 0;
    }
  }

  public static void tm_xy2geo(GEODETIC g)
  {
    double CM;		/* central meridian */
    double FE;		/* false easting */
    double FN;		/* false northing */
    double SF;		/* scale factor	*/
    double S0;		/* meridianal distance from equator */
	                /* to southernmost lat of zone */
    double x_adj;
    double y_adj;

    double OM;
    double COSOM;
    double FOOT;
    double SINF;
    double COSF;
    double TN;
    double TS;
    double RN;
    double Q;
    double QS;

    double ETS;
    double EPS;

    double E;
    double N;

    double B2, B3, B4, B5, B6, B7;
    double L;

    CM = g.cons.tm.CM;
    FE = g.cons.tm.FE;
    FN = g.cons.tm.FN;
    SF = g.cons.tm.SF;
    S0 = g.cons.tm.S0;
    x_adj = g.cons.tm.x_adj;
    y_adj = g.cons.tm.y_adj;

    E = feet2meters((double) g.data.x + x_adj);
    N = feet2meters((double) g.data.y + y_adj);

    OM = (N - FN + S0) / (SF * R);
    COSOM =java.lang.Math.cos(OM);

    FOOT = OM + java.lang.Math.sin(OM) * COSOM *
           (V0 + (V2*COSOM*COSOM) + (V4 * java.lang.Math.pow(COSOM, 4)) + (V6 * java.lang.Math.pow(COSOM, 6)));
    SINF = java.lang.Math.sin(FOOT);
    COSF = java.lang.Math.cos(FOOT);
    TN = SINF / COSF;
    TS = TN * TN;

    EPS = ESQ / (1.0 - ESQ);
    ETS = EPS * COSF * COSF;

    RN = (SF * ER) / java.lang.Math.sqrt(1.0 - ESQ * SINF * SINF);
    Q = (E - FE) / RN;
    QS = Q * Q;

    B2 = - ((TN * (1.0 + ETS)) / 2.0);
    B4 = - ((5.0 + (3.0 * TS) + ETS * (1.0 - (9.0 * TS)) - (4.0 * ETS * ETS)) / 12.0);
    B6 = (61.0 + 45.0 * TS * (2.0 + TS) + ETS*(46.0-252.0*TS - 60.0*TS*TS))/360.0;

    B3 = - (1.0 + TS + TS + ETS) / 6.0;
    B5 = (5.0+TS*(28.0+24.0*TS) + ETS*(6.0+8.0*TS))/120.0;
    B7 = -(61.0+662.0*TS+1320.0*TS*TS+720.0*java.lang.Math.pow(TS,3)) / 5040.0;

    L = Q * (1.0 + (QS * (B3 + (QS * (B5 + (B7 * QS))))));

    g.data.lon = (CM - (L / COSF)) / D2R;
    g.data.lat = (FOOT + B2 * QS * (1.0 + QS * (B4 + (B6 * QS)))) / D2R;

  }

  public int build_geodetic(int zone, float x_adj, float y_adj, GEODETIC g)
  {
    try
    {
      SPCS_DATA geo_data= new SPCS_DATA();
      int ret = 0;
      LAM data;
      LAM dataIn = new LAM();

      ret = get_spcs_data(zone, geo_data);
      
      if (ret == -1) 
      {
        return ret;
      }

      if (geo_data.type == 'L') 
      {
        data = calc_lam_constants(geo_data, dataIn);
        g.coordinates =COORDINATES.LAMBERT;
        data.x_adj = x_adj;
        data.y_adj = y_adj;
        g.cons.lambert = data;
      }
      else if (geo_data.type == 'T') 
      {
        TM data1=new TM();
        calc_tm_constants(geo_data, data1);
        g.coordinates = COORDINATES.T_MERCATOR;
        data1.x_adj = x_adj;
        data1.y_adj = y_adj;
        g.cons.tm = data1;
      }
      else 
      {
        ret = -1;
      }
  	
      return ret;
    }
    catch(Exception e)
    {
      e.printStackTrace(System.out);

      return 0;
    }
  }

  public static void calc_tm_constants(SPCS_DATA zone, TM constants)
  {
    double F;	/* flattening of the ellipsoid */
    double EPS;
    double PR;
    double EN;
    double A;
    double B;
    double C;
    double R0;
    double OM0;
    double S0;
    double SF;
    double OR;

    SF = 1.0 - 1.0/zone.data[3];
    OR = zone.data[2] * D2R;
    F = 1.0 / RF;
    EPS = ESQ / (1.0-ESQ);
    PR = (1.0-F) * ER;
    EN = (ER-PR)/(ER+PR);

    A = -1.5*EN + (9.0/16.0)*java.lang.Math.pow(EN, 3);
    B = 0.9375*EN*EN - (15.0/32.0)*java.lang.Math.pow(EN, 4);
    C = -(35.0/48.0)*java.lang.Math.pow(EN, 3);
    R0 = ER*(1.0-EN)*(1.0-EN*EN)*(1.0+2.25*EN*EN + (225.0/64.0)*java.lang.Math.pow(EN, 4));
    OM0 = OR + A*java.lang.Math.sin(2.0*OR) + B*java.lang.Math.sin(4.0*OR) + C*java.lang.Math.sin(6.0*OR);
    S0 = SF*R0*OM0;

    constants.CM = zone.data[0] * D2R;
    constants.A = A;
    constants.B = B;
    constants.C = C;
    constants.FE = zone.data[1];
    constants.FN = zone.data[4];
    constants.SF = SF;
    constants.S0 = S0;
  }

  public static LAM calc_lam_constants(SPCS_DATA zone, LAM constants)
  {
    double SINFS;
    double SINFN;
    double SINFB;
    double SINF0;
    double COSFN;
    double COSFS;
    double QS;
    double QN;
    double QB;
    double E;
    double W1;
    double W2;
    double K;
    double RB;

    LAM retval = new LAM();

    E =java.lang.Math.sqrt(ESQ);
    SINFS = java.lang.Math.sin(zone.data[3] * D2R);
    COSFS = java.lang.Math.cos(zone.data[3] * D2R);
    SINFN = java.lang.Math.sin(zone.data[4] * D2R);
    COSFN = java.lang.Math.cos(zone.data[4] * D2R);
    SINFB = java.lang.Math.sin(zone.data[5] * D2R);

    QS = Q(E, SINFS);
    QN = Q(E, SINFN);
    QB = Q(E, SINFB);

    W1 = java.lang.Math.sqrt(1.0 - (ESQ * java.lang.Math.pow(SINFS, 2)));
    W2 = java.lang.Math.sqrt(1.0 - (ESQ * java.lang.Math.pow(SINFN, 2)));

    SINF0 = java.lang.Math.log((W2*COSFS) / (W1*COSFN))/ (QN-QS);
    K = ER * COSFS * java.lang.Math.exp(QS*SINF0) / (W1*SINF0);
    RB = K / java.lang.Math.exp(QB*SINF0);

    constants.CM = zone.data[0] * D2R;
    constants.E0 = zone.data[1];
    constants.NB = zone.data[2];
    constants.SINF0 = SINF0;
    constants.RB = RB;
    constants.K = K;

    retval.CM = constants.CM;
    retval.E0 = constants.E0;
    retval.NB = constants.NB;
    retval.SINF0 = constants.SINF0;
    retval.RB = constants.RB;
    retval.K = constants.K;

    return retval;
  }

  public static double Q(double E, double S)
  {
    return (java.lang.Math.log((1+S) / (1-S)) - E * java.lang.Math.log((1+E*S) / (1-E*S))) / 2.0;
  }
}

class SPCS_DATA
{
  int zone_code;
  String desc;
  char type;
  double[] data = new double[6];
}

class CONVERSION
{
  public static int TO_XY = 0;
  public static int TO_LATLON = 1;
}	

class COORDINATES
{
  public static int LAMBERT =0;
  public static int T_MERCATOR = 1;
  public static int O_MERCATOR =2;
}

class spcs_dat
{
  public static double[][] DATA={{85.833336, 200000.000000, 30.500000, 25000.000000, 0.000000, 0.000000},
                                {87.500000, 600000.000000, 30.000000, 15000.000000, 0.000000, 0.000000},
                                {133.666672, 5000000.000000, 5000000.000000, 0.000000, 57.000000, 10000.000000},
                                {142.000000, 500000.000000, 54.000000, 10000.000000, 0.000000, 0.000000},
                                {146.000000, 500000.000000, 54.000000, 10000.000000, 0.000000, 0.000000},
                                {150.000000, 500000.000000, 54.000000, 10000.000000, 0.000000, 0.000000},
                                {154.000000, 500000.000000, 54.000000, 10000.000000, 0.000000, 0.000000},
                                {158.000000, 500000.000000, 54.000000, 10000.000000, 0.000000, 0.000000},
                                {162.000000, 500000.000000, 54.000000, 10000.000000, 0.000000, 0.000000},
                                {166.000000, 500000.000000, 54.000000, 10000.000000, 0.000000, 0.000000},
                                {170.000000, 500000.000000, 54.000000, 10000.000000, 0.000000, 0.000000},
                                {176.000000, 1000000.000000, 0.000000, 51.833332, 53.833332, 51.000000},
                                {110.166664, 213360.000000, 31.000000, 10000.000000, 0.000000, 0.000000},
                                {111.916664, 213360.000000, 31.000000, 10000.000000, 0.000000, 0.000000},
                                {113.750000, 213360.000000, 31.000000, 15000.000000, 0.000000, 0.000000},
                                {92.000000, 400000.000000, 0.000000, 34.933334, 36.233334, 34.333332},
                                {92.000000, 400000.000000, 400000.000000, 33.299999, 34.766666, 32.666668},
                                {122.000000, 2000000.000000, 500000.000000, 40.000000, 41.666668, 39.333332},
                                {122.000000, 2000000.000000, 500000.000000, 38.333332, 39.833332, 37.666668},
                                {120.500000, 2000000.000000, 500000.000000, 37.066666, 38.433334, 36.500000},
                                {119.000000, 2000000.000000, 500000.000000, 36.000000, 37.250000, 35.333332},
                                {118.000000, 2000000.000000, 500000.000000, 34.033333, 35.466667, 33.500000},
                                {116.250000, 2000000.000000, 500000.000000, 32.783333, 33.883335, 32.166668},
                                {105.500000, 914401.812500, 304800.593750, 39.716667, 40.783333, 39.333332},
                                {105.500000, 914401.812500, 304800.593750, 38.450001, 39.750000, 37.833332},
                                {105.500000, 914401.812500, 304800.593750, 37.233334, 38.433334, 36.666668},
                                {72.750000, 304800.593750, 152400.296875, 41.200001, 41.866665, 40.833332},
                                {75.416664, 200000.000000, 38.000000, 200000.000000, 0.000000, 0.000000},
                                {81.000000, 200000.000000, 24.333334, 17000.000000, 0.000000, 0.000000},
                                {82.000000, 200000.000000, 24.333334, 17000.000000, 0.000000, 0.000000},
                                {84.500000, 600000.000000, 0.000000, 29.583334, 30.750000, 29.000000},
                                {82.166664, 200000.000000, 30.000000, 10000.000000, 0.000000, 0.000000},
                                {84.166664, 700000.000000, 30.000000, 10000.000000, 0.000000, 0.000000},
                                {155.500000, 500000.000000, 18.833334, 30000.000000, 0.000000, 0.000000},
                                {156.666672, 500000.000000, 20.333334, 30000.000000, 0.000000, 0.000000},
                                {158.000000, 500000.000000, 21.166666, 100000.000000, 0.000000, 0.000000},
                                {159.500000, 500000.000000, 21.833334, 100000.000000, 0.000000, 0.000000},
                                {160.166672, 500000.000000, 21.666666, 1.000000, 0.000000, 0.000000},
                                {112.166664, 200000.000000, 41.666668, 19000.000000, 0.000000, 0.000000},
                                {114.000000, 500000.000000, 41.666668, 19000.000000, 0.000000, 0.000000},
                                {115.750000, 800000.000000, 41.666668, 15000.000000, 0.000000, 0.000000},
                                {88.333336, 300000.000000, 36.666668, 40000.000000, 0.000000, 0.000000},
                                {90.166664, 700000.000000, 36.666668, 17000.000000, 0.000000, 0.000000},
                                {85.666664, 100000.000000, 37.500000, 30000.000000, 250000.000000, 0.000000},
                                {87.083336, 900000.000000, 37.500000, 30000.000000, 250000.000000, 0.000000},
                                {93.500000, 1500000.000000, 1000000.000000, 42.066666, 43.266666, 41.500000},
                                {93.500000, 500000.000000, 0.000000, 40.616665, 41.783333, 40.000000},
                                {98.000000, 400000.000000, 0.000000, 38.716667, 39.783333, 38.333332},
                                {98.500000, 400000.000000, 400000.000000, 37.266666, 38.566666, 36.666668},
                                {84.250000, 500000.000000, 0.000000, 37.966667, 38.966667, 37.500000},
                                {85.750000, 500000.000000, 500000.000000, 36.733334, 37.933334, 36.333332},
                                {92.500000, 1000000.000000, 0.000000, 31.166666, 32.666668, 30.500000},
                                {91.333336, 1000000.000000, 0.000000, 29.299999, 30.700001, 28.500000},
                                {91.333336, 1000000.000000, 0.000000, 26.166666, 27.833334, 25.500000},
                                {68.500000, 300000.000000, 43.666668, 10000.000000, 0.000000, 0.000000},
                                {70.166664, 900000.000000, 42.833332, 30000.000000, 0.000000, 0.000000},
                                {77.000000, 400000.000000, 0.000000, 38.299999, 39.450001, 37.666668},
                                {71.500000, 200000.000000, 750000.000000, 41.716667, 42.683334, 41.000000},
                                {70.500000, 500000.000000, 0.000000, 41.283333, 41.483334, 41.000000},
                                {87.000000, 8000000.000000, 0.000000, 45.483334, 47.083332, 44.783333},
                                {84.366669, 6000000.000000, 0.000000, 44.183334, 45.700001, 43.316666},
                                {84.366669, 4000000.000000, 0.000000, 42.099998, 43.666668, 41.500000},
                                {93.099998, 800000.000000, 100000.000000, 47.033333, 48.633335, 46.500000},
                                {94.250000, 800000.000000, 100000.000000, 45.616665, 47.049999, 45.000000},
                                {94.000000, 800000.000000, 100000.000000, 43.783333, 45.216667, 43.000000},
                                {88.833336, 300000.000000, 29.500000, 20000.000000, 0.000000, 0.000000},
                                {90.333336, 700000.000000, 29.500000, 20000.000000, 0.000000, 0.000000},
                                {90.500000, 250000.000000, 35.833332, 15000.000000, 0.000000, 0.000000},
                                {92.500000, 500000.000000, 35.833332, 15000.000000, 0.000000, 0.000000},
                                {94.500000, 850000.000000, 36.166668, 17000.000000, 0.000000, 0.000000},
                                {109.500000, 600000.000000, 0.000000, 45.000000, 49.000000, 44.250000},
                                {100.000000, 500000.000000, 0.000000, 40.000000, 43.000000, 39.833332},
                                {115.583336, 200000.000000, 34.750000, 10000.000000, 8000000.000000, 0.000000},
                                {116.666664, 500000.000000, 34.750000, 10000.000000, 6000000.000000, 0.000000},
                                {118.583336, 800000.000000, 34.750000, 10000.000000, 4000000.000000, 0.000000},
                                {71.666664, 300000.000000, 42.500000, 30000.000000, 0.000000, 0.000000},
                                {74.500000, 150000.000000, 38.833332, 10000.000000, 0.000000, 0.000000},
                                {104.333336, 165000.000000, 31.000000, 11000.000000, 0.000000, 0.000000},
                                {106.250000, 500000.000000, 31.000000, 10000.000000, 0.000000, 0.000000},
                                {107.833336, 830000.000000, 31.000000, 12000.000000, 0.000000, 0.000000},
                                {74.500000, 150000.000000, 38.833332, 10000.000000, 0.000000, 0.000000},
                                {76.583336, 250000.000000, 40.000000, 16000.000000, 0.000000, 0.000000},
                                {78.583336, 350000.000000, 40.000000, 16000.000000, 0.000000, 0.000000},
                                {74.000000, 300000.000000, 0.000000, 40.666668, 41.033333, 40.166668},
                                {79.000000, 609601.250000, 0.000000, 34.333332, 36.166668, 33.750000},
                                {100.500000, 600000.000000, 0.000000, 47.433334, 48.733334, 47.000000},
                                {100.500000, 600000.000000, 0.000000, 46.183334, 47.483334, 45.666668},
                                {82.500000, 600000.000000, 0.000000, 40.433334, 41.700001, 39.666668},
                                {82.500000, 600000.000000, 0.000000, 38.733334, 40.033333, 38.000000},
                                {98.000000, 600000.000000, 0.000000, 35.566666, 36.766666, 35.000000},
                                {98.000000, 600000.000000, 0.000000, 33.933334, 35.233334, 33.333332},
                                {120.500000, 2500000.000000, 0.000000, 44.333332, 46.000000, 43.666668},
                                {120.500000, 1500000.000000, 0.000000, 42.333332, 44.000000, 41.666668},
                                {77.750000, 600000.000000, 0.000000, 40.883335, 41.950001, 40.166668},
                                {77.750000, 600000.000000, 0.000000, 39.933334, 40.966667, 39.333332},
                                {71.500000, 100000.000000, 41.083332, 160000.000000, 0.000000, 0.000000},
                                {81.000000, 609600.000000, 0.000000, 32.500000, 34.833332, 31.833334},
                                {100.000000, 600000.000000, 0.000000, 44.416668, 45.683334, 43.833332},
                                {100.333336, 600000.000000, 0.000000, 42.833332, 44.400002, 42.333332},
                                {86.000000, 600000.000000, 0.000000, 35.250000, 36.416668, 34.333332},
                                {101.500000, 200000.000000, 1000000.000000, 34.650002, 36.183334, 34.000000},
                                {98.500000, 600000.000000, 2000000.000000, 32.133335, 33.966667, 31.666666},
                                {100.333336, 700000.000000, 3000000.000000, 30.116667, 31.883333, 29.666666},
                                {99.000000, 600000.000000, 4000000.000000, 28.383333, 30.283333, 27.833334},
                                {98.500000, 300000.000000, 5000000.000000, 26.166666, 27.833334, 25.666666},
                                {111.500000, 500000.000000, 1000000.000000, 40.716667, 41.783333, 40.333332},
                                {111.500000, 500000.000000, 2000000.000000, 39.016666, 40.650002, 38.333332},
                                {111.500000, 500000.000000, 3000000.000000, 37.216667, 38.349998, 36.666668},
                                {72.500000, 500000.000000, 42.500000, 28000.000000, 0.000000, 0.000000},
                                {78.500000, 3500000.000000, 2000000.000000, 38.033333, 39.200001, 37.666668},
                                {78.500000, 3500000.000000, 1000000.000000, 36.766666, 37.966667, 36.333332},
                                {120.833336, 500000.000000, 0.000000, 47.500000, 48.733334, 47.000000},
                                {120.500000, 500000.000000, 0.000000, 45.833332, 47.333332, 45.333332},
                                {79.500000, 600000.000000, 0.000000, 39.000000, 40.250000, 38.500000},
                                {81.000000, 600000.000000, 0.000000, 37.483334, 38.883335, 37.000000},
                                {90.000000, 600000.000000, 0.000000, 45.566666, 46.766666, 45.166668},
                                {90.000000, 600000.000000, 0.000000, 44.250000, 45.500000, 43.833332},
                                {90.000000, 600000.000000, 0.000000, 42.733334, 44.066666, 42.000000},
                                {105.166664, 200000.000000, 40.500000, 16000.000000, 0.000000, 0.000000},
                                {107.333336, 400000.000000, 40.500000, 16000.000000, 100000.000000, 0.000000},
                                {108.750000, 600000.000000, 40.500000, 16000.000000, 0.000000, 0.000000},
                                {110.083336, 800000.000000, 40.500000, 16000.000000, 100000.000000, 0.000000},
                                {66.433334, 200000.000000, 200000.000000, 18.033333, 18.433332, 17.833334},
                                {0.000000, 0.000000, 0.000000, 0.000000, 0.000000, 0.000000},
                                {213.000000, 500000.000000, 0.000000, 2500.000000, 0.000000, 0.000000}
                              };
public  static int[] ZONE ={101,102,5001,5002,5003,5004,5005,5006,5007,5008,5009,
                                5010,201,202,203,301,302,401,402,403,404,405,406,501,
                                502,503,600,700,901,902,903,1001,1002,5101,5102,5103,
                                5104,5105,1101,1102,1103,1201,1202,1301,1302,1401,1402,
                                1501,1502,1601,1602,1701,1702,1703,1801,1802,1900,2001,
                                2002,2111,2112,2113,2201,2202,2203,2301,2302,2401,2402,
                                2403,2500,2600,2701,2702,2703,2800,2900,3001,3002,3003,
                                3101,3102,3103,3104,3200,3301,3302,3401,3402,3501,3502,
                                3601,3602,3701,3702,3800,3900,4001,4002,4100,4201,4202,
                                4203,4204,4205,4301,4302,4303,4400,4501,4502,4601,4602,
                                4701,4702,4801,4802,4803,4901,4902,4903,4904,5200,5300,
                                5400};

public  static String[] DESCR={"AL E","AL W","AK 1","AK 2","AK 3","AK 4","AK 5","AK 6",
                                  "AK 7","AK 8","AK 9","AK10","AZ E","AZ C","AZ W","AR N",
                                  "AR S","CA 1","CA 2","CA 3","CA 4","CA 5","CA 6","CO N",
                                  "CO C","CO S","CT  ","DE  ","FL E","FL W","FL N","GA E",
                                  "GA W","HI 1","HI 2","HI 3","HI 4","HI 5","ID E","ID C",
                                  "ID W","IL E","IL W","IN E","IN W","IA N","IA S","KS N",
                                  "KS S","KY N","KY S","LA N","LA S","LASH","ME E","ME W",
                                  "MD  ","MA M","MA I","MI N","MI C","MI S","MN N","MN C",
                                  "MN S","MS E","MS W","MO E","MO C","MO W","MT  ","NE  ",
                                  "NV E","NV C","NV W","NH  ","NJ  ","NM E","NM C","NM W",
                                  "NY E","NY C","NY W","NY L","NC  ","ND N","ND S","OH N",
                                  "OH S","OK N","OK S","OR N","OR S","PA N","PA S","RI  ",
                                  "SC  ","SD N","SD S","TN  ","TX N","TXNC","TX C","TXSC",
                                  "TX S","UT N","UT C","UT S","VT  ","VA N","VA S","WA N",
                                  "WA S","WV N","WV S","WI N","WI C","WI S","WY E",
                                  "WYEC","WYWC","WY W","PRVI","AS  ","GU  "
                               };

  static char[] TYPE={'T','T','O','T','T','T','T','T','T','T','T','L','T','T','T',
                              'L','L','L','L','L','L','L','L','L','L','L','L','T','T','T',
                              'L','T','T','T','T','T','T','T','T','T','T','T','T','T','T',
                              'L','L','L','L','L','L','L','L','L','T','T','L','L','L',
                              'L','L','L','L','L','L','T','T','T','T','T','L','L','T',
                              'T','T','T','T','T','T','T','T','T','T','L','L','L','L',
                              'L','L','L','L','L','L','L','L','T','L','L','L','L','L',
                              'L','L','L','L','L','L','L','T','L','L','L','L','L','L',
                              'L','L','L','T','T','T','T','L','N','T'
                              };
}







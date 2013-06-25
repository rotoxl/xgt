------------------------------------------------
-- Export file for user EMBLA_RG_MAP          --
-- Created by fcs513j on 12/12/2011, 11:59:08 --
------------------------------------------------

spool PACKAGES.log

prompt
prompt Creating package RPT
prompt ====================
prompt
CREATE OR REPLACE PACKAGE "RPT"            AS
 ---Version Embla PACK-3.421.211052

TYPE t_cursor IS REF CURSOR;

PROCEDURE usp_rpt_prueba(p IN VARCHAR2, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_RegistroES(p IN INTEGER, q IN VARCHAR2,r IN INTEGER, s IN VARCHAR2,t IN VARCHAR2,cursorOut OUT t_cursor);

PROCEDURE usp_RPT_Certificados(p IN VARCHAR2,q IN VARCHAR2, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_Certificados_tc(p IN VARCHAR2,q IN VARCHAR2, r IN VARCHAR2,
                                  s IN VARCHAR2, t IN INTEGER,u IN INTEGER,cursorOut OUT t_cursor);
 ---Version Embla Body-8.180.224068

-- 29/03/2006  se añade un paránmetro para que coja el logo para INE-IGAE
PROCEDURE usp_RPT_Certificados_Jasper(p IN VARCHAR2,q IN VARCHAR2,KK IN VARCHAR2, s IN  VARCHAR2,
                                      t IN VARCHAR2,u IN VARCHAR2,v IN VARCHAR2, cursorOut OUT t_cursor);

 ---Version Embla Body-8.200.212053
PROCEDURE usp_RPT_Certificados_inte(p IN VARCHAR2,q IN VARCHAR2,KK IN VARCHAR2, s IN  VARCHAR2,
                                      t IN VARCHAR2,u IN VARCHAR2,v IN VARCHAR2, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_Certificados_2200(p IN VARCHAR2,q IN VARCHAR2,KK IN VARCHAR2, s IN  VARCHAR2,
                                      t IN VARCHAR2,u IN VARCHAR2,v IN VARCHAR2, cursorOut OUT t_cursor);

FUNCTION usp_RPT_Certificados_Jasperaa(p IN VARCHAR2,q IN VARCHAR2,r IN DATE,s IN  VARCHAR2,
                                      t IN VARCHAR2,u IN VARCHAR2,v IN VARCHAR2) RETURN Rpt.t_cursor;

PROCEDURE usp_RPT_CertificadosTotal(p IN VARCHAR2,q IN VARCHAR2,r IN DATE,s IN VARCHAR2,t IN VARCHAR2, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_CorreosCount(p IN VARCHAR2,q IN DATE,r IN DATE,s IN INTEGER, t IN INTEGER,
                               u IN VARCHAR2, v IN VARCHAR2 ,cursorOut OUT t_cursor);

FUNCTION oficina(ofi IN VARCHAR2) RETURN VARCHAR2;

FUNCTION orgadestino(ejer INTEGER, tipo IN VARCHAR2,numrg IN INTEGER)RETURN VARCHAR2;

FUNCTION nombreybarra(ejer INTEGER, tipo IN VARCHAR2,numrg IN INTEGER)RETURN VARCHAR2;

FUNCTION nosonnulos(orgdo VARCHAR2, cdp VARCHAR2)RETURN NUMBER;

FUNCTION nonulosorganos(orgdo VARCHAR2) RETURN NUMBER;

PROCEDURE usp_RPT_Interesadosrecibo(p IN INTEGER,q IN VARCHAR2, r IN INTEGER, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_Interesadosrecibo_MAP(p IN INTEGER,q IN VARCHAR2, r IN INTEGER,cursorOut OUT t_cursor);

PROCEDURE usp_RPT_Interesados_HIS(p IN INTEGER,q IN VARCHAR2, r IN INTEGER,cursorOut OUT t_cursor);

-- 29/03/2006  Procedimiento que queda para Tribunal de Cuentas
PROCEDURE usp_RPT_FichaRegistro(p IN INTEGER,q IN VARCHAR2,r IN INTEGER,
                               s IN VARCHAR2, cursorOut OUT t_cursor) ;

--29/03/2006  Procedimiento para INE-IGAE para que coja el logo
PROCEDURE usp_RPT_FichaRegistro_2(p IN INTEGER,q IN VARCHAR2,r IN INTEGER,
                               s IN VARCHAR2, t IN VARCHAR2,cursorOut OUT t_cursor) ;

PROCEDURE usp_RPT_FichaRegistro_2_MIN(p IN INTEGER,q IN VARCHAR2,r IN INTEGER,
                               s IN VARCHAR2, t IN VARCHAR2,cursorOut OUT t_cursor) ;

PROCEDURE usp_RPT_RegistroGeneral(p IN  INTEGER, q IN VARCHAR2,r IN VARCHAR2,
                                  s IN INTEGER , cursorOut OUT t_cursor);

PROCEDURE usp_RPT_InformesConImagenes(p IN VARCHAR2,r IN  INTEGER,fv IN  DATE,
                                      ff IN DATE, cursorOut OUT t_cursor);

 PROCEDURE usp_RPT_Interesados(cursorOut OUT t_cursor);

 PROCEDURE usp_RPT_Correos(p IN  VARCHAR2,q IN DATE, r IN  DATE, s IN INTEGER,t IN INTEGER,
                           u IN VARCHAR2, v IN VARCHAR2, cursorOut OUT t_cursor);

 PROCEDURE usp_RPT_IndicesE(p IN  VARCHAR2, q IN INTEGER, r IN INTEGER, s IN INTEGER, t IN INTEGER,
                            u IN VARCHAR2 , cursorOut OUT t_cursor);

 ---Version Embla Body-8.151.225645
 PROCEDURE usp_RPT_IndicesE2(p IN VARCHAR2,q IN INTEGER, r IN INTEGER, s IN  INTEGER,
                             t IN INTEGER, u IN VARCHAR2, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_IndicesE2_fechas(p IN VARCHAR2,fv IN DATE,ff IN DATE, s IN INTEGER,
                               t IN INTEGER, u IN VARCHAR2 , cursorOut OUT t_cursor);

PROCEDURE usp_RPT_IndicesE2_fechas_MIN(p IN VARCHAR2,fv IN DATE,ff IN DATE, s IN INTEGER,
                               t IN INTEGER, u IN VARCHAR2 , cursorOut OUT t_cursor);

PROCEDURE usp_RPT_IndicesE_fechas(p IN VARCHAR2, kk IN  VARCHAR2, cc IN VARCHAR2,
                                  s IN INTEGER, t IN INTEGER, u IN VARCHAR2  , cursorOut OUT t_cursor);

PROCEDURE usp_RPT_IndicesE3_fechas_MIN(p IN VARCHAR2, kk IN  VARCHAR2, cc IN VARCHAR2,
                                  s IN INTEGER, t IN INTEGER, u IN VARCHAR2  , cursorOut OUT t_cursor);

--28/03/2006  para INE-IGAE con  un parámetro más para que coja el LOGO
PROCEDURE usp_RPT_IndicesE_fechas_2(p IN VARCHAR2, kk IN  VARCHAR2, cc IN VARCHAR2,
                                  s IN INTEGER, t IN INTEGER, u IN VARCHAR2,
                                  v IN VARCHAR2, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_IndicesE_fechas_MIN(p IN VARCHAR2, kk IN VARCHAR2, cc IN VARCHAR2,
                                      s IN INTEGER, t IN INTEGER, u IN VARCHAR2,
                                      v IN VARCHAR2, w IN INTEGER, x IN INTEGER, y IN INTEGER, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_IndicesEDes_fechas_MIN(p IN VARCHAR2, kk IN VARCHAR2, cc IN VARCHAR2,
                                      s IN INTEGER, t IN INTEGER, u IN VARCHAR2,
                                      v IN VARCHAR2, w IN INTEGER, x IN INTEGER, y IN INTEGER,
                                      z IN VARCHAR2, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_IndicesE_fechas_MAP(p IN VARCHAR2, kk IN VARCHAR2, cc IN VARCHAR2,
                                      s IN INTEGER, t IN INTEGER, u IN VARCHAR2,
                                      v IN VARCHAR2, w IN INTEGER, x IN INTEGER,
                                      y IN VARCHAR2, z IN VARCHAR2, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_IndicesE_FPre_2200
        (p IN VARCHAR2, kk IN VARCHAR2, cc IN VARCHAR2,
         s IN INTEGER, t IN INTEGER, u IN VARCHAR2,
         v IN VARCHAR2, w IN INTEGER, x IN INTEGER,
         y IN VARCHAR2, z IN VARCHAR2, -- z concatena mostrar suborganos y el tipo asunto
         cursorOut OUT t_cursor);

PROCEDURE usp_RPT_IndicesE_INT_MAP (p IN VARCHAR2,kk IN VARCHAR2,cc IN VARCHAR2,s IN INTEGER,t IN INTEGER,
                                    r in VARCHAR2, v IN VARCHAR2, x IN INTEGER, cursorOut OUT RPT.t_cursor);

PROCEDURE usp_RPT_IndicesE_MAP_SELEC(p IN VARCHAR2, q IN VARCHAR2, cursorOut OUT t_cursor);
/*
PROCEDURE usp_RPT_IndicesE_fechas_MIN(p IN VARCHAR2, kk IN VARCHAR2, cc IN VARCHAR2,
                                  s IN INTEGER, t IN INTEGER, u IN VARCHAR2,
                                  v IN VARCHAR2, w IN INTEGER, cursorOut OUT t_cursor);
*/

PROCEDURE usp_RPT_IndicesE_Destino(P_USUARIO  IN VARCHAR2, -- Uusuario
                                   P_OFICINA        IN VARCHAR2, -- Código de oficina (letra O)
                                   P_ORGANO         IN VARCHAR2, -- Código de órgano (letra A)
                                   P_DEP            IN VARCHAR2, -- Código de departamento (letra B)
                                   P_F_INI          IN DATE, -- Fecha Inicio (Filtro)
                                   P_F_FIN          IN DATE, -- Fecha Final (Filtro)
                                   P_REG_INI        IN INTEGER, -- Num Registro Inicio (Filtro)
                                   P_REG_FIN        IN INTEGER, -- Num Registro Final (Filtro)
                                   P_FILTRO_DIV     IN INTEGER, -- Código de División (Filtro)
                                   P_FILTRO_ORG     IN VARCHAR2, -- Código de Órgano (Filtro)
                                   cursorOut        OUT t_cursor);

 ---Version Embla Body-8.180.223968
 PROCEDURE usp_RPT_IndicesS(p IN VARCHAR2,q IN INTEGER, r IN INTEGER,s IN INTEGER,
                           t IN INTEGER , cursorOut OUT t_cursor);

-- PROCEDURE usp_RPT_IndicesS_fechas(p in varchar2,fv  in date, ff  in date, s in  integer,
--                                   t in integer , cursorOut out t_cursor);

 PROCEDURE usp_RPT_IndicesS_fechas(p IN VARCHAR2,kk IN  VARCHAR2, cc IN VARCHAR2, s IN  INTEGER,
                                   t IN INTEGER , cursorOut OUT t_cursor);

 PROCEDURE usp_RPT_IndicesS_fechas_2000(p IN VARCHAR2,kk IN  VARCHAR2, cc IN VARCHAR2, s IN  INTEGER,
                                   t IN INTEGER , P_Org_Origen IN VARCHAR2, cursorOut OUT t_cursor);

  PROCEDURE usp_RPT_IndicesS_fechas_2(p IN VARCHAR2,kk IN  VARCHAR2, cc IN VARCHAR2, s IN  INTEGER,
                                   t IN INTEGER ,u IN VARCHAR2, cursorOut OUT t_cursor);

  PROCEDURE usp_RPT_IndicesS_fechas_MIN(p IN VARCHAR2,kk IN  VARCHAR2, cc IN VARCHAR2, s IN  INTEGER,
                                   t IN INTEGER ,u IN VARCHAR2, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_IndicesS_fechas_MAP(p IN VARCHAR2, kk IN VARCHAR2, cc IN VARCHAR2,
 s IN INTEGER, t IN INTEGER, u IN VARCHAR2, v IN VARCHAR2, w IN VARCHAR2, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_IndicesS_FPre_2200(p IN VARCHAR2, kk IN VARCHAR2, cc IN VARCHAR2,
 s IN INTEGER, t IN INTEGER, u IN VARCHAR2, v IN VARCHAR2, cursorOut OUT t_cursor);

  PROCEDURE usp_RPT_ENT_ORI_MAP(p IN VARCHAR2, kk IN VARCHAR2, cc IN VARCHAR2,
   s IN INTEGER, t IN INTEGER, u IN VARCHAR2, cursorOut OUT t_cursor);

 ---Version Embla Body-8.164.234017
--PROCEDURE usp_RPT_IndicesSD_fechas(p in varchar2, fv in date, ff in date,
--                                    s in integer,t in integer , cursorOut out t_cursor);

PROCEDURE usp_RPT_IndicesSD_fechas(p IN VARCHAR2, kk IN  VARCHAR2, cc IN VARCHAR2,
                                    s IN INTEGER,t IN INTEGER , cursorOut OUT t_cursor);

PROCEDURE usp_RPT_IndicesSD_fechas_2000(p IN VARCHAR2, kk IN  VARCHAR2, cc IN VARCHAR2,
                                    s IN INTEGER,t IN INTEGER , P_Org_Destino IN VARCHAR2, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_IndicesSD_fechas_2(p IN VARCHAR2, kk IN  VARCHAR2, cc IN VARCHAR2,
                                    s IN INTEGER,t IN INTEGER,u IN VARCHAR2, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_IndicesSD_fechas_MAP
(p IN VARCHAR2,
 kk IN VARCHAR2,
 cc IN VARCHAR2,
 s IN INTEGER,
 t IN INTEGER,
 u IN VARCHAR2,
 v IN INTEGER,
 w IN VARCHAR2,
 x IN VARCHAR2,  -- Tipo Asunto
 y IN VARCHAR2,  -- Subórgano
 cursorOut OUT t_cursor);

PROCEDURE usp_RPT_IndicesSD_FPre_2200
(p IN VARCHAR2,
 kk IN VARCHAR2,
 cc IN VARCHAR2,
 s IN INTEGER,
 t IN INTEGER,
 u IN VARCHAR2,
 v IN INTEGER,
 w IN VARCHAR2,
 x IN VARCHAR2,  -- Tipo Asunto
 cursorOut OUT t_cursor);

PROCEDURE usp_RPT_IndicesSD_fechas_MIN(p IN VARCHAR2, kk IN  VARCHAR2, cc IN VARCHAR2,
                                    s IN INTEGER,t IN INTEGER,u IN VARCHAR2, cursorOut OUT t_cursor);

--PROCEDURE usp_RPT_IndicesSDI_fechas(p in varchar2,fv in date,ff in date,s in integer,
--                                    t in integer, cursorOut out t_cursor);

PROCEDURE usp_RPT_IndicesSDI_fechas(p IN VARCHAR2,kk IN  VARCHAR2, cc IN VARCHAR2, s IN INTEGER,
                                    t IN INTEGER, x IN VARCHAR2,cursorOut OUT t_cursor);

PROCEDURE usp_RPT_IndicesSDI_fechas_2(p IN VARCHAR2,kk IN  VARCHAR2, cc IN VARCHAR2, s IN INTEGER,
                                    t IN INTEGER, u IN VARCHAR2, cursorOut OUT t_cursor);

 ---Version Embla Body-8.171.224754
PROCEDURE usp_RPT_LibroRegistro(p IN INTEGER,q IN VARCHAR2, fv IN DATE,
                                 ff IN DATE, cursorOut OUT t_cursor);

-- 27/03/2006 Creado para sacar los Logo en INE e IGAE  añade parámetro ú
PROCEDURE usp_RPT_LibroRegistro_2(p IN INTEGER,q IN VARCHAR2, fv IN DATE,
                                 ff IN DATE, ú IN VARCHAR2, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_LibroRegistro_MIN(p IN INTEGER,q IN VARCHAR2, fv IN DATE,
                                 ff IN DATE, ú IN VARCHAR2, cursorOut OUT t_cursor);

 ---Version Embla Body-8.151.225721
PROCEDURE usp_RPT_LibroRegistro_fechas(p IN INTEGER,q IN VARCHAR2,fv IN DATE,
                                       ff IN DATE, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_Organos (P_Division IN INTEGER,
                           P_Organo   IN VARCHAR2,
                           P_Descrip  IN VARCHAR2,
                           cursorOut OUT t_cursor);

PROCEDURE usp_RPT_Pendientes1(p IN VARCHAR2, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_Pendientes2(p VARCHAR2 , cursorOut OUT t_cursor);

PROCEDURE usp_RPT_Pendientes3(p VARCHAR2 , cursorOut OUT t_cursor);

PROCEDURE usp_RPT_Registro(p IN  VARCHAR2,q IN VARCHAR2,
                           r IN INTEGER, s IN INTEGER, t IN INTEGER, cursorOut OUT t_cursor);

--PROCEDURE usp_RPT_Registro_fechas(p in varchar2, q in varchar2, r in integer,
--fv in date, ff in date, cursorOut out t_cursor);

PROCEDURE usp_RPT_Registro_fechas(p IN VARCHAR2, q IN VARCHAR2, r IN INTEGER,
kk IN  VARCHAR2, cc IN VARCHAR2,x IN VARCHAR2,
cursorOut OUT t_cursor);

 ---Version Embla Body-8.188.231852

PROCEDURE usp_RPT_Registro_fechas_tc(p IN VARCHAR2, q IN VARCHAR2, r IN INTEGER,
kk IN  VARCHAR2, cc IN VARCHAR2,
cursorOut OUT t_cursor);

PROCEDURE usp_RPT_Convocatorias_tc(p IN VARCHAR2, q IN INTEGER, r IN INTEGER,
s IN  VARCHAR2, t IN INTEGER, u IN VARCHAR2, kk IN  VARCHAR2, cc IN  VARCHAR2,
cursorOut OUT t_cursor);

PROCEDURE usp_RPT_Convocatorias_MIN(p IN VARCHAR2, q IN INTEGER, r IN INTEGER,
s IN  VARCHAR2, t IN INTEGER, u IN VARCHAR2, kk IN  VARCHAR2, cc IN  VARCHAR2,
cursorOut OUT t_cursor);

PROCEDURE usp_RPT_Registro_fechas_MIN(p IN VARCHAR2, q IN VARCHAR2, r IN NUMBER,
kk IN VARCHAR2, cc IN VARCHAR2,x IN VARCHAR2,
--fv in date, ff in date,
cursorOut OUT t_cursor);

PROCEDURE usp_RPT_Registro_fechas_MAP(p IN VARCHAR2, q IN VARCHAR2, r IN NUMBER,
kk IN VARCHAR2, cc IN VARCHAR2,x IN VARCHAR2, v IN VARCHAR2, w IN VARCHAR2, P_TipoAsunto IN INTEGER,
cursorOut OUT t_cursor);

PROCEDURE usp_RPT_Registro_FPre_2200(p IN VARCHAR2, q IN VARCHAR2, r IN NUMBER,
kk IN VARCHAR2, cc IN VARCHAR2,x IN VARCHAR2, v IN VARCHAR2, w IN VARCHAR2, P_TipoAsunto IN INTEGER,
cursorOut OUT t_cursor);

PROCEDURE usp_RPT_IndicesSDI_2_MIN(p IN VARCHAR2,
--fv in date,ff in date,
kk IN VARCHAR2, cc IN VARCHAR2,
s IN NUMBER, t IN NUMBER, u IN VARCHAR2,  cursorOut OUT t_cursor);

PROCEDURE usp_RPT_IndicesSDI_2_2200(p IN VARCHAR2,
--fv in date,ff in date,
kk IN VARCHAR2, cc IN VARCHAR2,
s IN NUMBER, t IN NUMBER, u IN VARCHAR2, v IN VARCHAR2, w IN VARCHAR2, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_IndicesSDI_FPre_2200(p IN VARCHAR2,
--fv in date,ff in date,
kk IN VARCHAR2, cc IN VARCHAR2,
s IN NUMBER, t IN NUMBER, u IN VARCHAR2, v IN VARCHAR2, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_FichaRegistro_MIN(p IN INTEGER,q IN VARCHAR2, r IN INTEGER,
                               s IN VARCHAR2, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_IndicesSDI_MIN(p IN VARCHAR2,
kk IN VARCHAR2, cc IN VARCHAR2,
s IN INTEGER, t IN INTEGER, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_Documentos_MAP(u IN VARCHAR2,o IN VARCHAR2,r IN VARCHAR2,s IN VARCHAR2, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_Compulsas_MAP(u IN VARCHAR2,o IN VARCHAR2,r IN VARCHAR2,s IN VARCHAR2, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_FichaReg_MAP(p IN VARCHAR2, q IN INTEGER,
                                       r IN VARCHAR2, s IN INTEGER, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_CertificadoReg_MAP(p IN INTEGER, q IN VARCHAR2, r IN INTEGER,
                               s IN VARCHAR2, t IN VARCHAR2, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_CertificadoReg_HIS(p IN INTEGER, q IN VARCHAR2, r IN INTEGER,
                               s IN VARCHAR2, t IN VARCHAR2, cursorOut OUT t_cursor);

PROCEDURE usp_RPT_ReciboConcurso_MAP(p IN VARCHAR2, q IN VARCHAR2, r IN INTEGER,
                               s IN VARCHAR2, t IN VARCHAR2, u IN VARCHAR2, cursorOut OUT t_cursor);

END RPT;
/

prompt
prompt Creating package RPT_HEMBLA
prompt ===========================
prompt
CREATE OR REPLACE PACKAGE "RPT_HEMBLA" AS
 ---Version Embla PACK-3.159.199825


TYPE t_cursor IS REF CURSOR;

PROCEDURE usp_RPT_CertificadoReg (p IN INTEGER,
                                      q IN VARCHAR2,
                                      r IN INTEGER,
                                      s IN VARCHAR2,
                                      t IN VARCHAR2,
                                      cursorOut OUT t_cursor);

PROCEDURE usp_RPT_FichaReg (p IN VARCHAR2,
                            q IN INTEGER,
                            r IN VARCHAR2,
                            s IN INTEGER,
                            cursorOut OUT t_cursor);

PROCEDURE usp_RPT_IndicesE_SELEC (p IN VARCHAR2,
                                  q IN VARCHAR2,
                                  cursorOut OUT t_cursor);


PROCEDURE usp_RPT_Interesadosrecibo (p IN INTEGER,
                                     q IN VARCHAR2,
                                     r IN INTEGER,
                                     cursorOut OUT t_cursor);


PROCEDURE usp_RPT_Registro_fechas (p IN VARCHAR2,
                                   q IN VARCHAR2,
                                   r IN NUMBER,
                                   kk IN VARCHAR2,
                                   cc IN VARCHAR2,
                                   x IN VARCHAR2,
                                   v IN VARCHAR2,
                                   w IN VARCHAR2,
                                   P_TipoAsunto IN INTEGER,
                                   cursorOut OUT t_cursor);

END RPT_HEMBLA;
/

prompt
prompt Creating package body RPT
prompt =========================
prompt
CREATE OR REPLACE PACKAGE BODY "RPT"                      AS

/*************************************************************************************************/
PROCEDURE usp_rpt_prueba(p IN VARCHAR2, cursorOut OUT t_cursor)
IS
  ret_cursor t_cursor;

BEGIN
  OPEN ret_cursor FOR

      SELECT  *
      FROM AUX_FIRMANTES
      WHERE cd_firmante = p;

  cursorOut:=ret_cursor;
END usp_rpt_prueba;


/*************************************************************************************************/
PROCEDURE usp_RPT_RegistroES(p IN INTEGER, q IN VARCHAR2,r IN INTEGER, s IN VARCHAR2,t IN VARCHAR2,cursorOut OUT t_cursor)
IS
  ret_cursor t_cursor;

BEGIN
  OPEN ret_cursor FOR

    SELECT  ES.CD_Oficina, ES.TipoES, ES.Fecha,
    	ES.Num_Registro, ES.Usuario,ES.CD_Organo_Destino,
    	ES.CD_Organo_Origen, ES.CD_REgis_Original,
    	ES.Tipo_Regis_original,ES.Num_Regis_original,
    	ES.Fecha_Regis_Original, ES.Tipo_transporte,
    	ES.Num_TRansporte, ES.Resumen
    FROM
         	REGISTROES ES, INTERESADOS I,REL_INTVSRES IR
    WHERE
    		ES.Num_Registro = IR.Num_Registro
    	AND I.CD_Interesado = IR.CD_Interesado
    	AND (ES.Num_Registro = p OR p = 0)
    	--AND (ES.Num_Registro = p OR p is null)
    	AND (ES.TipoES = q OR q IS NULL)
    	AND (ES.CD_Oficina = s OR s IS NULL)
    	AND (ES.Usuario = t OR t IS NULL)
    	AND (p IS NOT NULL OR q IS NOT NULL OR s IS NOT NULL OR t IS NOT NULL);

  cursorOut:=ret_cursor;
END usp_RPT_RegistroES;


/*************************************************************************************************/
PROCEDURE usp_RPT_Certificados(p IN VARCHAR2,q IN VARCHAR2, cursorOut OUT t_cursor) IS
  ret_cursor t_cursor;

BEGIN
  OPEN ret_cursor FOR

        SELECT  I.* , ES.Num_RegistroOF,
        	 ES.Num_sobres, ES.Fecha

        FROM    REL_INTVSRES I, REGISTROES ES
        WHERE ES.CD_Oficina = p
        	AND ES.ExpOferta = q
        	AND I.Ejercicio = ES.Ejercicio
        	AND I.TipoES = ES.TipoES
        	AND I.Num_Registro = ES.Num_Registro
        	AND I.CD_Interesado = 1

        ORDER BY I.Ejercicio, I.TipoES, I.Num_Registro;

	 cursorOut:=ret_cursor;
END usp_RPT_Certificados;


/************************************************************************************************/
procedure usp_RPT_Certificados_tc(p         IN VARCHAR2,
                                  q         IN VARCHAR2,
                                  r         IN VARCHAR2,
                                  s         IN VARCHAR2,
                                  t         IN INTEGER,
                                  u         IN INTEGER,
                                  cursorOut OUT t_cursor) IS
  ret_cursor t_cursor;

BEGIN

  OPEN ret_cursor FOR

    SELECT ES.EJERCICIO,
           ES.TIPOES,
           (CASE
             WHEN (SELECT Num_RG
                     FROM REGISTROGENERAL RG
                    WHERE RG.Ejercicio = ES.Ejercicio
                      AND RG.TipoES = ES.TipoES
                      AND RG.CD_Oficina = ES.CD_Oficina
                      AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL THEN
              --TO_CHAR(ES.Num_RegistroOF) || '/RG ' ||
              TO_CHAR(ES.Num_RegistroOF) || NVL2(OFI.SIGLAOF,' (' || OFI.SIGLAOF || ') ','') || '/RG ' ||
              TO_CHAR((SELECT Num_RG
                        FROM REGISTROGENERAL RG
                       WHERE RG.Ejercicio = ES.Ejercicio
                         AND RG.TipoES = ES.TipoES
                         AND RG.CD_Oficina = ES.CD_Oficina
                         AND RG.Num_Registro = ES.Num_RegistroOF))
             ELSE
              --TO_CHAR(ES.Num_RegistroOF)
              TO_CHAR(ES.Num_RegistroOF) || NVL2(OFI.SIGLAOF,' (' || OFI.SIGLAOF || ') ','')
           END) AS NUM_REGISTRO,
           I.CD_INTERESADO,
           I.NOMBRE AS NOMBRE,
           I.NIF,
           I.LOCALIDAD AS LOCALIDAD,
           I.CP,
           CASE
             WHEN I.Provincia_EXT IS NULL THEN
              (SELECT DS_Provincia
                 FROM AUX_PROVINCIAS PROVI
                WHERE I.Provincia = PROVI.CD_PROVINCIA)
             ELSE
              I.Provincia_EXT
           END AS NOMBREPROVINCIA,
           I.PAIS AS PAIS,
           ES.NUM_REGISTROOF,
           ES.Num_sobres,
           ES.Fecha,
           ES.TIPO_TRANSPORTE,
           tr.DS_Transporte AS DS_TRANSPORTE,
           OFI.DIRECCION AS DIRECCION

      FROM REL_INTVSRES    I,
           REGISTROES      ES,
           AUX_TRANSPORTES TR,
           AUX_OFICINAS    OFI
     WHERE ES.CD_Oficina = p
       AND ES.CD_OFICINA = OFI.CD_OFICINA
       AND I.Ejercicio = ES.Ejercicio
       AND I.TipoES = ES.TipoES
       AND I.Num_Registro = ES.Num_Registro
       AND I.CD_Interesado =
           (SELECT MIN(CD_INTERESADO)
              FROM REL_INTVSRES RINT
             WHERE RINT.Ejercicio = ES.Ejercicio
               AND RINT.TipoES = ES.TipoES
               AND RINT.Num_Registro = ES.Num_Registro
             GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
       AND (ES.TIPO_TRANSPORTE = ES.TIPO_TRANSPORTE)
       AND (ES.TIPO_TRANSPORTE = TR.CD_TRANSPORTE)
       and (ES.TIPO_TRANSPORTE = q or q is null)
       AND (ES.FECHA = TO_DATE(r, 'dd/mm/yyyy') or r is null)
       AND (ES.TIPOES = s or s is null)
       AND (ES.Num_RegistroOF >= t OR t = 0)
       AND (ES.Num_RegistroOF <= u OR u = 0)

     ORDER BY I.Ejercicio, tr.DS_Transporte, I.TipoES, I.Num_Registro;

  cursorOut := ret_cursor;
END usp_RPT_Certificados_tc;


/************************************************************************************************/
-- usp_RPT_Certificados_Jasper       Modificado  14-11-2005
PROCEDURE usp_RPT_Certificados_Jasper(p IN VARCHAR2,q IN VARCHAR2,
kk IN VARCHAR2,s IN  VARCHAR2,t IN VARCHAR2,u IN VARCHAR2,v IN VARCHAR2, cursorOut OUT t_cursor)IS

  ret_cursor t_cursor;

r DATE := TO_DATE(kk, 'DD/MM/YYYY');

BEGIN

  OPEN ret_cursor FOR

SELECT I.* ,
TO_CHAR((CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE
            RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE
			  RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END)) AS "NumRegistro",
 ES.Num_sobres,
(TO_CHAR(r, 'DD') || ' de '||
DECODE(TO_CHAR(r, 'MM'),1,'Enero'
	 ,2,'Febrero'
	 ,3,'Marzo'
	 ,4 , 'Abril'
	 ,5 , 'Mayo'
	 ,6 , 'Junio'
	 ,7 , 'Julio'
	 ,8 , 'Agosto'
	 ,9 ,'Septiembre'
	 ,10 ,'Octubre'
	 ,11 , 'Noviembre'
	 ,12 , 'Diciembre', ' ')
|| ' de ' || TO_CHAR(r, 'YYYY'))
AS fecha,
 FIR.DS_Firmante AS Firmante1,
 FIR.DS_Cargo AS Cargo1,
 OFI.DS_Oficina,
OFI.Poblacion AS Poblacion, OFI.Direccion AS Direccion,
OFI.Telefono AS Tel, OFI.Fax AS Fax, OFI.CP AS CP, ES.ExpOferta,
TO_CHAR(ES.FechaP,'DD/MM/YYYY HH24:MI:SS') AS fecharegistro,
 -- 103 formato dd/mm/yy
 		(select c.DS_Logo
  from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and
    uu.CD_Usuario = v)
   as Organismo

FROM    REL_INTVSRES I, REGISTROES ES, AUX_OFICINAS OFI,
DAT_CONFIG CC, USUARIOS uuu,
 AUX_FIRMANTES FIR
WHERE ES.CD_Oficina = p
	AND ES.CD_Oficina = OFI.CD_Oficina
	AND UPPER(ES.ExpOferta) = UPPER(q)
	AND I.Ejercicio = ES.Ejercicio
	AND I.TipoES = ES.TipoES
	AND I.Num_Registro = ES.Num_Registro
	AND I.CD_Interesado=
	(SELECT MIN(CD_INTERESADO) FROM REL_INTVSRES RINT
	  WHERE RINT.Ejercicio = ES.Ejercicio AND
	     RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
	    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		    AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND UUU.CD_USUARIO = v
	AND FIR.CD_Firmante = s
ORDER BY I.Ejercicio, I.TipoES, I.Num_Registro;

	 cursorOut:=ret_cursor;
END usp_RPT_Certificados_Jasper;



/*************************************************************************************************/
-- Procedimiento para Ministerio del Interior PC MIN-RG-06-8
PROCEDURE usp_RPT_Certificados_inte(p IN VARCHAR2, q IN VARCHAR2,
          kk IN VARCHAR2, s IN  VARCHAR2, t IN VARCHAR2, u IN VARCHAR2,
          v IN VARCHAR2, cursorOut OUT t_cursor)
IS
  ret_cursor t_cursor;
  r DATE := TO_DATE(kk, 'DD/MM/YYYY');

BEGIN
  OPEN ret_cursor FOR

    SELECT I.* ,
      TO_CHAR(
      (CASE WHEN (SELECT Num_RG
	              FROM REGISTROGENERAL RG
                  WHERE RG.Ejercicio = ES.Ejercicio
				        AND	RG.TipoES = ES.TipoES
                   		AND RG.CD_Oficina = ES.CD_Oficina
                   		AND RG.Num_Registro = ES.Num_RegistroOF
						AND (UPPER(I.NOMBRE) = UPPER(u)
/*    	     				OR I.NOMBRE like (SELECT UPPER(RINT.NOMBRE)
							                  FROM REL_INTVSRES RINT
											  WHERE RINT.Ejercicio = ES.Ejercicio
                							  		AND RINT.TipoES = ES.TipoES
													AND RINT.Num_Registro = ES.Num_Registro)
*/   						OR u is null)
    					AND I.Ejercicio = ES.Ejercicio
    					AND I.TipoES = ES.TipoES
    					AND I.Num_Registro = ES.Num_Registro
        				and ES.CD_Oficina = p
    					AND UPPER(ES.ExpOferta) = UPPER(q)
    					AND (ES.FECHA <= r  or r is null)) IS NOT NULL
      		THEN TO_CHAR(ES.Num_RegistroOF) || '/RG ' ||
      		     TO_CHAR((SELECT Num_RG
				          FROM REGISTROGENERAL RG
                  		  WHERE RG.Ejercicio = ES.Ejercicio
				          		AND	RG.TipoES = ES.TipoES
								AND RG.CD_Oficina = ES.CD_Oficina
                   				AND RG.Num_Registro = ES.Num_RegistroOF
								AND (UPPER(I.NOMBRE) = UPPER(u)
/*    	     						OR I.NOMBRE like (SELECT UPPER(RINT.NOMBRE)
							            		      FROM REL_INTVSRES RINT
											  		  WHERE RINT.Ejercicio = ES.Ejercicio
                							  		  		AND RINT.TipoES = ES.TipoES
															AND RINT.Num_Registro = ES.Num_Registro)
*/   								OR u is null)
    							AND I.Ejercicio = ES.Ejercicio
    							AND I.TipoES = ES.TipoES
    							AND I.Num_Registro = ES.Num_Registro
        						AND ES.CD_Oficina = p
    							AND UPPER(ES.ExpOferta) = UPPER(q)
    							AND (ES.FECHA <= r  or r is null)))
      		 ELSE TO_CHAR(ES.Num_RegistroOF)
      	END)) AS "NumRegistro",
      ES.Num_sobres,
      TO_CHAR(SYSDATE, 'DD') || ' de ' ||
         DECODE(TO_CHAR(SYSDATE,'MM')
         ,01, 'Enero'
      	 ,02, 'Febrero'
      	 ,03, 'Marzo'
      	 ,04, 'Abril'
      	 ,05, 'Mayo'
      	 ,06, 'Junio'
      	 ,07, 'Julio'
      	 ,08, 'Agosto'
      	 ,09, 'Septiembre'
      	 ,10, 'Octubre'
      	 ,11, 'Noviembre'
      	 ,12, 'Diciembre', ' ')
         || ' de ' || TO_CHAR(SYSDATE, 'YYYY') AS fechafirma,
      FIR.DS_Firmante AS Firmante1,
      FIR.DS_Cargo AS Cargo1,
      OFI.DS_Oficina,
      LOC.DS_LOCALIDAD AS Poblacion,
      OFI.Direccion AS Direccion,
      OFI.Telefono AS Tel, OFI.Fax AS Fax, OFI.CP AS CP, ES.ExpOferta,
      TO_CHAR(ES.FechaP,'DD/MM/YYYY HH24:MI:SS') AS fecharegistro,
      TO_CHAR(ES.Fecha,'DD/MM/YYYY') AS fecha,
      (select c.DS_Logo
       from Dat_Config c inner join USUARIOS uu on
          uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = v)
       as Organismo
    FROM
      REL_INTVSRES I, REGISTROES ES, AUX_OFICINAS OFI,
      DAT_CONFIG CC, USUARIOS uuu,
      AUX_FIRMANTES FIR, AUX_LOCALIDADES LOC
    WHERE
        ES.CD_Oficina = p
    	AND ES.CD_Oficina = OFI.CD_Oficina
    	AND UPPER(ES.ExpOferta) = UPPER(q)
    	AND I.Ejercicio = ES.Ejercicio
    	AND I.TipoES = ES.TipoES
    	AND I.Num_Registro = ES.Num_Registro
    	AND (ES.FECHA <= r  or r is null)
    	AND (UPPER(I.NOMBRE) = UPPER(u)
/*    	     OR I.NOMBRE like (SELECT UPPER(RINT.NOMBRE) FROM
    	         REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio
                AND RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro)
*/           OR u is null)
    	/*
    	AND	I.CD_Interesado =
    	(SELECT MIN(CD_INTERESADO) FROM REL_INTVSRES RINT
    	  WHERE RINT.Ejercicio = ES.Ejercicio
        AND RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    	  GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
      */
    	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
    	AND UUU.CD_USUARIO = v
    	AND FIR.CD_Firmante = s
      AND OFI.CD_PROVINCIA = LOC.CD_PROVINCIA
      AND OFI.CD_LOCALIDAD = LOC.CD_LOCALIDAD
    ORDER BY
      I.Ejercicio, I.TipoES, I.Num_Registro;

	cursorOut:=ret_cursor;
END usp_RPT_Certificados_inte;


/*************************************************************************************************/


PROCEDURE usp_RPT_Certificados_2200(p IN VARCHAR2, q IN VARCHAR2,
          kk IN VARCHAR2, s IN  VARCHAR2, t IN VARCHAR2, u IN VARCHAR2,
          v IN VARCHAR2, cursorOut OUT t_cursor)
IS
  ret_cursor t_cursor;
  r DATE := TO_DATE(kk, 'DD/MM/YYYY');

BEGIN
  OPEN ret_cursor FOR

    SELECT I.* ,
      TO_CHAR(
      (CASE WHEN (SELECT Num_RG
	              FROM REGISTROGENERAL RG
                  WHERE RG.Ejercicio = ES.Ejercicio
				        AND	RG.TipoES = ES.TipoES
                   		AND RG.CD_Oficina = ES.CD_Oficina
                   		AND RG.Num_Registro = ES.Num_RegistroOF
						AND (UPPER(I.NOMBRE) = UPPER(u)
 						OR u is null)
    					AND I.Ejercicio = ES.Ejercicio
    					AND I.TipoES = ES.TipoES
    					AND I.Num_Registro = ES.Num_Registro
        				and ES.CD_Oficina = p
    					AND UPPER(ES.ExpOferta) = UPPER(q)
    					AND (ES.FECHA <= r  or r is null)) IS NOT NULL
      		THEN TO_CHAR(ES.Num_RegistroOF) || '/RG ' ||
      		     TO_CHAR((SELECT Num_RG
				          FROM REGISTROGENERAL RG
                  		  WHERE RG.Ejercicio = ES.Ejercicio
				          		AND	RG.TipoES = ES.TipoES
								AND RG.CD_Oficina = ES.CD_Oficina
                   				AND RG.Num_Registro = ES.Num_RegistroOF
								AND (UPPER(I.NOMBRE) = UPPER(u)
  								OR u is null)
    							AND I.Ejercicio = ES.Ejercicio
    							AND I.TipoES = ES.TipoES
    							AND I.Num_Registro = ES.Num_Registro
        						AND ES.CD_Oficina = p
    							AND UPPER(ES.ExpOferta) = UPPER(q)
    							AND (ES.FECHA <= r  or r is null)))
      		 ELSE TO_CHAR(ES.Num_RegistroOF)
      	END)) AS "NumRegistro",
      ES.Num_sobres,
      TO_CHAR(SYSDATE, 'DD') || ' de ' ||
         DECODE(TO_CHAR(SYSDATE,'MM')
         ,01, 'Enero'
      	 ,02, 'Febrero'
      	 ,03, 'Marzo'
      	 ,04, 'Abril'
      	 ,05, 'Mayo'
      	 ,06, 'Junio'
      	 ,07, 'Julio'
      	 ,08, 'Agosto'
      	 ,09, 'Septiembre'
      	 ,10, 'Octubre'
      	 ,11, 'Noviembre'
      	 ,12, 'Diciembre', ' ')
         || ' de ' || TO_CHAR(SYSDATE, 'YYYY') AS fechafirma,
      FIR.DS_Firmante AS Firmante1,
      FIR.DS_Cargo AS Cargo1,
      OFI.DS_Oficina,
      LOC.DS_LOCALIDAD AS Poblacion,
      OFI.Direccion AS Direccion,
      OFI.Telefono AS Tel, OFI.Fax AS Fax, OFI.CP AS CP, ES.ExpOferta,
      TO_CHAR(ES.FechaP,'DD/MM/YYYY HH24:MI:SS') AS fecharegistro,
      TO_CHAR(ES.Fecha,'DD/MM/YYYY') AS fecha,
      (select c.DS_Logo
       from Dat_Config c inner join USUARIOS uu on
          uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = v)
       as Organismo
    FROM
      REL_INTVSRES I, REGISTROES ES, AUX_OFICINAS OFI,
      DAT_CONFIG CC, USUARIOS uuu,
      AUX_FIRMANTES FIR, AUX_LOCALIDADES LOC
    WHERE
        ES.CD_Oficina = p
    	AND ES.CD_Oficina = OFI.CD_Oficina
    	AND UPPER(ES.ExpOferta) = UPPER(q)
    	AND I.Ejercicio = ES.Ejercicio
    	AND I.TipoES = ES.TipoES
    	AND I.Num_Registro = ES.Num_Registro
    	AND (ES.FECHA <= r  or r is null)
    	AND (UPPER(I.NOMBRE) = UPPER(u)
           OR u is null)
    	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
    	AND UUU.CD_USUARIO = v
    	AND FIR.CD_Firmante = s
      AND OFI.CD_PROVINCIA = LOC.CD_PROVINCIA
      AND OFI.CD_LOCALIDAD = LOC.CD_LOCALIDAD
    ORDER BY
      I.Ejercicio, I.TipoES, I.Num_Registro;

	cursorOut:=ret_cursor;
END usp_RPT_Certificados_2200;


/*************************************************************************************************/

FUNCTION usp_RPT_Certificados_Jasperaa(p IN VARCHAR2,q IN VARCHAR2,r IN DATE,s IN  VARCHAR2,
                                t IN VARCHAR2,u IN VARCHAR2,v IN VARCHAR2)RETURN Rpt.t_cursor IS
  ret_cursor Rpt.t_cursor;

BEGIN
  OPEN ret_cursor FOR


SELECT  I.* , ES.Num_RegistroOF, ES.Num_sobres,
(TO_CHAR(r) || ' de '||
DECODE(TO_CHAR(r, 'MM'),1,'Enero'
	 ,2,'Febrero'
	 ,3,'Marzo'
	 ,4 , 'Abril'
	 ,5 , 'Mayo'
	 ,6 , 'Junio'
	 ,7 , 'Julio'
	 ,8 , 'Agosto'
	 ,9 ,'Septiembre'
	 ,10 ,'Octubre'
	 ,11 , 'Noviembre'
	 ,12 , 'Diciembre', ' ')
|| ' de ' || TO_CHAR(r))
AS fecha, FIR.DS_Firmante AS Firmante1, FIR2.DS_Firmante AS Firmante2,
FIR.DS_Cargo AS Cargo1, FIR2.DS_Cargo AS Cargo2, OFI.DS_Oficina,
OFI.Poblacion AS Poblacion, OFI.Direccion AS Direccion,
OFI.Telefono AS Tel, OFI.Fax AS Fax, OFI.CP AS CP, ES.ExpOferta,
TO_CHAR(ES.FechaP,'DD/MM/YYYY HH24:MI:SS') AS fecharegistro -- 103 formato dd/mm/yy
FROM    REL_INTVSRES I, REGISTROES ES, AUX_OFICINAS OFI, AUX_FIRMANTES FIR,
AUX_FIRMANTES FIR2
WHERE ES.CD_Oficina = p
	AND ES.CD_Oficina = OFI.CD_Oficina
	AND ES.ExpOferta = q
	AND I.Ejercicio = ES.Ejercicio
	AND I.TipoES = ES.TipoES
	AND I.Num_Registro = ES.Num_Registro
	AND I.CD_Interesado = 1
	AND FIR.CD_Firmante = s
	AND FIR2.CD_Firmante = t

ORDER BY I.Ejercicio, I.TipoES, I.Num_Registro;
	   RETURN ret_cursor;
END usp_RPT_Certificados_Jasperaa;


/*************************************************************************************************/
PROCEDURE usp_RPT_CertificadosTotal(p IN VARCHAR2,q IN VARCHAR2,r IN DATE,s IN VARCHAR2,t IN VARCHAR2, cursorOut OUT t_cursor)IS
  ret_cursor t_cursor;

  fecha  DATE := r ;

BEGIN
  OPEN ret_cursor FOR

    SELECT  fecha, MAX(FIR.DS_Firmante) AS Firmante1, MAX(FIR2.DS_Firmante) AS Firmante2, MAX(FIR.DS_Cargo) AS Cargo1, MAX(FIR2.DS_Cargo) AS Cargo2, OFI.DS_Oficina, MAX(OFI.Poblacion) AS Poblacion,
     MAX(OFI.Direccion) AS Direccion, MAX(OFI.Telefono) AS Tel, MAX(OFI.Fax) AS Fax, MAX(OFI.CP) AS CP, COUNT(ES.CD_Oficina) AS Numero, ES.ExpOferta
    FROM    REGISTROES ES, AUX_OFICINAS OFI, AUX_FIRMANTES FIR, AUX_FIRMANTES FIR2
    WHERE ES.CD_Oficina = p
    	AND ES.ExpOferta = q
    	AND ES.CD_Oficina = OFI.CD_Oficina
    	AND FIR.CD_Firmante = s
    	AND FIR2.CD_Firmante = t

    GROUP BY OFI.DS_Oficina, ES.ExpOferta;

	 cursorOut:=ret_cursor;
END usp_RPT_CertificadosTotal;


/*************************************************************************************************/
PROCEDURE usp_RPT_CorreosCount(p IN VARCHAR2,q IN DATE,r IN DATE, s IN INTEGER, t IN INTEGER,
                               u IN VARCHAR2, v IN VARCHAR2 ,cursorOut OUT t_cursor)
IS
  ret_cursor t_cursor;
  fv  DATE := q;
  ff  DATE := r;

  BEGIN
  OPEN ret_cursor FOR

SELECT COUNT (OFI.DS_Oficina) AS total,
 v  AS "ofi",
OFI.DS_Oficina,
OFI.NIF_Oficina
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS OG1,
	AUX_DEPARTAMENTOS DEP1
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND ES.CD_Organo_Destino=DEP1.CD_Organo(+)
	AND ES.CD_Dep_Destino=DEP1.CD_Departamento(+)
	AND (ES.CD_Division_Destino=OG1.CD_Division(+))
	AND (ES.CD_Organo_Destino=OG1.CD_Organo(+))
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'S'
	AND ES.Fecha  BETWEEN fv AND ff
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	AND (q IS NOT NULL OR r IS NOT NULL)
	-- AND (ES.Num_RegistroOF >= s OR s is Null)
	-- AND (ES.Num_RegistroOF <= t OR t is Null)
	AND ES.Estado IS NULL
	AND ES.Tipo_Transporte = u

GROUP BY OFI.DS_Oficina,
OFI.NIF_Oficina;

cursorOut:=ret_cursor;
END usp_RPT_CorreosCount;



/*************************************************************************************************/
FUNCTION orgadestino(ejer INTEGER, tipo IN VARCHAR2,numrg IN INTEGER)RETURN VARCHAR2 IS

 dsresultado VARCHAR2(100) := NULL;

BEGIN
SELECT MAX(Nombre) INTO dsresultado FROM REL_INTVSRES INT WHERE INT.Ejercicio = ejer AND
				INT.TipoES = tipo AND INT.Num_Registro = numrg
				GROUP BY INT.Ejercicio, INT.TipoES, INT.Num_Registro;
RETURN dsresultado;
END orgadestino;


/*************************************************************************************************/
FUNCTION oficina(ofi IN VARCHAR2)RETURN VARCHAR2  IS
resultadoofi VARCHAR2(100) := NULL;

BEGIN
SELECT AUX_OFICINAS.DS_Oficina INTO resultadoofi FROM AUX_OFICINAS
  WHERE AUX_OFICINAS.CD_Oficina = ofi;
  RETURN  resultadoofi;
END oficina;


/*************************************************************************************************/
FUNCTION nombreybarra(ejer INTEGER, tipo IN VARCHAR2,numrg IN INTEGER)RETURN VARCHAR2 IS

 dsresultado VARCHAR2(100) := NULL;

BEGIN
SELECT ' / ' || MAX(Nombre) INTO dsresultado FROM REL_INTVSRES INT WHERE INT.Ejercicio = ejer AND
				INT.TipoES = tipo AND INT.Num_Registro = numrg
				GROUP BY INT.Ejercicio, INT.TipoES, INT.Num_Registro;
RETURN dsresultado;
END nombreybarra;


/*************************************************************************************************/
FUNCTION nosonnulos(orgdo VARCHAR2, cdp VARCHAR2)
          RETURN NUMBER  IS
 valor NUMBER := 0;

BEGIN
    IF (orgdo <> '' AND cdp <> '' ) THEN
       valor := 1;
    END IF;

 RETURN valor;

END nosonnulos;


/*************************************************************************************************/
FUNCTION nonulosorganos(orgdo VARCHAR2)
          RETURN NUMBER  IS
 valor NUMBER := 0;

BEGIN
    IF (orgdo <> '') THEN
       valor := 2;
    END IF;

 RETURN valor;

END nonulosorganos;


/*************************************************************************************************/
PROCEDURE usp_RPT_Interesadosrecibo(p IN INTEGER,q IN VARCHAR2, r IN INTEGER,cursorOut OUT t_cursor) IS
  ret_cursor t_cursor;

BEGIN
  OPEN ret_cursor FOR

    SELECT * FROM REL_INTVSRES
    WHERE (REL_INTVSRES.EJERCICIO = p AND
    REL_INTVSRES.TIPOES =q AND
    REL_INTVSRES.NUM_REGISTRO = r);

  cursorOut:=ret_cursor;
END usp_RPT_Interesadosrecibo;


/*************************************************************************************************/
PROCEDURE usp_RPT_Interesadosrecibo_MAP(p IN INTEGER,q IN VARCHAR2, r IN INTEGER,cursorOut OUT t_cursor)
IS
  ret_cursor t_cursor;

BEGIN
  OPEN ret_cursor FOR

      SELECT *
      FROM REL_INTVSRES
      WHERE REL_INTVSRES.EJERCICIO = p
      AND REL_INTVSRES.TIPOES = q
      AND REL_INTVSRES.NUM_REGISTRO = r;

  cursorOut:=ret_cursor;

END usp_RPT_Interesadosrecibo_MAP;

/*************************************************************************************************/
PROCEDURE usp_RPT_Interesados_HIS(p IN INTEGER,q IN VARCHAR2, r IN INTEGER,cursorOut OUT t_cursor)
IS
  ret_cursor t_cursor;

BEGIN
  OPEN ret_cursor FOR

      SELECT *
      FROM REL_INTVSRES_HIS
      WHERE REL_INTVSRES_HIS.EJERCICIO = p
      AND REL_INTVSRES_HIS.TIPOES = q
      AND REL_INTVSRES_HIS.NUM_REGISTRO = r;

  cursorOut:=ret_cursor;

END usp_RPT_Interesados_HIS;

/*************************************************************************************************/
-- 29/03/2006  proced.queda para Tribunal de Cuentas
PROCEDURE usp_RPT_FichaRegistro(p IN INTEGER,q IN VARCHAR2, r IN INTEGER,
                               s IN VARCHAR2, cursorOut OUT t_cursor) IS
  ret_cursor t_cursor;

BEGIN
  OPEN ret_cursor FOR

SELECT DISTINCT ES.*,
	s AS Firmante,
	DECODE(ES.TipoES,'E',
		'Remitentes:',
		 'Destinatarios:') AS Desc_Interesados,
	OFI.DS_Oficina,
	OFI.Poblacion,
	OFI.Direccion,
	OFI.Telefono,
	OFI.Fax,
	OFI.CP,
	PR.DS_Provincia,
	TRE.DS_TipoRegistro,
	TR.DS_Transporte,
	TRE1.DS_TipoRegistro AS DS_TipoRegOriginal,
		(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	(CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
			(SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN CD_Organo_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo
				ELSE  OG.DS_Organo || ' (' || (SELECT  UPPER(AUX_OrganosV1.DS_Organo) FROM AUX_OrganosV1 WHERE
					UPPER(OG.CD_Organosup)=UPPER(AUX_OrganosV1.CD_Organo(+))) || ')'
				END

		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Organo_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
			(SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG1.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN CD_Organo_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo
				ELSE  OG1.DS_Organo || ' (' || (SELECT AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG1.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	TO_CHAR(SYSDATE, 'DD') || ' de ' ||
     DECODE(TO_CHAR(SYSDATE, 'MM'),01,'Enero'
	 ,02,'Febrero'
	 ,03,'Marzo'
	 ,04 , 'Abril'
	 ,05 , 'Mayo'
	 ,06 , 'Junio'
	 ,07 , 'Julio'
	 ,08 , 'Agosto'
	 ,09 ,'Septiembre'
	 ,10 ,'Octubre'
	 ,11 , 'Noviembre'
	 ,12 , 'Diciembre', ' ')
|| ' de ' || TO_CHAR(SYSDATE, 'YYYY') AS FechaHoy
FROM
      	REGISTROES ES, --REL_INTvsREs IR,
	AUX_OFICINAS OFI, AUX_PROVINCIAS PR,
	AUX_TIPOREGISTRO TRE, AUX_TIPOREGISTRO TRE1,AUX_TRANSPORTES TR,  AUX_ORGANOS OG,
	AUX_ORGANOS OG1,
	AUX_DEPARTAMENTOS DEP,
	AUX_DEPARTAMENTOS DEP1

WHERE

		ES.Ejercicio = p
	AND ES.TipoES = q
	AND ES.Num_Registro = r
	--AND IR.Ejercicio=ES.Ejercicio
	--AND IR.TipoES=ES.TipoES
	--AND IR.Num_Registro=ES.Num_Registro
	AND ES.CD_Oficina = OFI.CD_Oficina
	AND (OFI.Provincia=PR.CD_Provincia(+))
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND ES.TipoES = TRE.CD_TipoRegistro
	AND (ES.CD_Organo_Origen=OG.CD_Organo(+))
	AND (UPPER(ES.CD_Organo_Destino)=UPPER(OG1.CD_Organo(+)))
	AND (ES.Tipo_Regis_Original=TRE1.CD_TipoRegistro(+))
	AND (ES.CD_Organo_Origen=DEP.CD_Organo(+))
	--AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_Organo_Destino=DEP1.CD_Organo(+));
	--AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+));

  cursorOut:=ret_cursor;
END usp_RPT_FichaRegistro;



/*************************************************************************************************/
-- 29/03/2006  proced.queda para INE-IGAE para que coja el LOGO
PROCEDURE usp_RPT_FichaRegistro_2(p IN INTEGER,q IN VARCHAR2, r IN INTEGER,
                               s IN VARCHAR2,t IN VARCHAR2, cursorOut OUT t_cursor) IS
  ret_cursor t_cursor;

BEGIN
  OPEN ret_cursor FOR

SELECT DISTINCT ES.*,
	s AS Firmante,
	DECODE(ES.TipoES,'E',
		'Remitentes:',
		 'Destinatarios:') AS Desc_Interesados,
	OFI.DS_Oficina,
	OFI.Poblacion,
	OFI.Direccion,
	OFI.Telefono,
	OFI.Fax,
	OFI.CP,
	PR.DS_Provincia,
	TRE.DS_TipoRegistro,
	TR.DS_Transporte,
	TRE1.DS_TipoRegistro AS DS_TipoRegOriginal,
		(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	(CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
			(SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN CD_Organo_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo
				ELSE  OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')'
				END

		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Organo_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
			(SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG1.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN CD_Organo_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo
				ELSE  OG1.DS_Organo || ' (' || (SELECT AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG1.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	TO_CHAR(SYSDATE, 'DD') || ' de ' ||
     DECODE(TO_CHAR(SYSDATE, 'MM'),01,'Enero'
	 ,02,'Febrero'
	 ,03,'Marzo'
	 ,04 , 'Abril'
	 ,05 , 'Mayo'
	 ,06 , 'Junio'
	 ,07 , 'Julio'
	 ,08 , 'Agosto'
	 ,09 ,'Septiembre'
	 ,10 ,'Octubre'
	 ,11 , 'Noviembre'
	 ,12 , 'Diciembre', ' ')
|| ' de ' || TO_CHAR(SYSDATE, 'YYYY') AS FechaHoy,
	(select c.DS_Logo
  from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and
    uu.CD_Usuario = t)
   as Organismo
FROM
      	REGISTROES ES, --REL_INTvsREs IR,
	AUX_OFICINAS OFI, AUX_PROVINCIAS PR,
	AUX_TIPOREGISTRO TRE, AUX_TIPOREGISTRO TRE1 ,
	AUX_TRANSPORTES TR,
	DAT_CONFIG CC, USUARIOS uuu,
	  AUX_ORGANOS OG,
	AUX_ORGANOS OG1,
	AUX_DEPARTAMENTOS DEP,
	AUX_DEPARTAMENTOS DEP1

WHERE

		ES.Ejercicio = p
	AND ES.TipoES = q
	AND ES.Num_Registro = r
	--AND IR.Ejercicio=ES.Ejercicio
	--AND IR.TipoES=ES.TipoES
	--AND IR.Num_Registro=ES.Num_Registro
	AND ES.CD_Oficina = OFI.CD_Oficina
	AND (OFI.Provincia=PR.CD_Provincia(+))
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND ES.TipoES = TRE.CD_TipoRegistro
	AND (ES.CD_Organo_Origen=OG.CD_Organo(+))
	AND (ES.CD_Organo_Destino=OG1.CD_Organo(+))
	AND (ES.Tipo_Regis_Original=TRE1.CD_TipoRegistro(+))
	AND (ES.CD_Organo_Origen=DEP.CD_Organo(+))
	--AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_Organo_Destino=DEP1.CD_Organo(+))
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND UUU.CD_USUARIO = t;
	--AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+));

  cursorOut:=ret_cursor;
END usp_RPT_FichaRegistro_2;


/*************************************************************************************************/
PROCEDURE usp_RPT_FichaRegistro_2_MIN(p IN INTEGER,q IN VARCHAR2, r IN INTEGER,
                               s IN VARCHAR2,t IN VARCHAR2, cursorOut OUT t_cursor) IS
  ret_cursor t_cursor;

BEGIN
  OPEN ret_cursor FOR

SELECT DISTINCT ES.*,
	s AS Firmante,
	DECODE(ES.TipoES,'E',
		'Remitentes:',
		 'Destinatarios:') AS Desc_Interesados,
	OFI.DS_Oficina,
	OFI.Poblacion,
	OFI.Direccion,
	OFI.Telefono,
	OFI.Fax,
	OFI.CP,
	PR.DS_Provincia,
	TRE.DS_TipoRegistro,
	TR.DS_Transporte,
	TRE1.DS_TipoRegistro AS DS_TipoRegOriginal,
		(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	(CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
			(SELECT DS_Organo FROM AUX_Organos_PROD WHERE
				OG.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN OG.DS_Organo
				ELSE OG.DS_Organo || ' (' ||
         (SELECT DS_Organo FROM AUX_Organos_PROD WHERE
					OG.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)) || ')'
				END
		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
			(SELECT DS_Organo FROM AUX_Organos_PROD WHERE
				OG1.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo
				ELSE  OG1.DS_Organo || ' (' ||
         (SELECT DS_Organo FROM AUX_Organos_PROD WHERE
					OG1.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	TO_CHAR(SYSDATE, 'DD') || ' de ' ||
     DECODE(TO_CHAR(SYSDATE, 'MM'),01,'Enero'
	 ,02,'Febrero'
	 ,03,'Marzo'
	 ,04 , 'Abril'
	 ,05 , 'Mayo'
	 ,06 , 'Junio'
	 ,07 , 'Julio'
	 ,08 , 'Agosto'
	 ,09 ,'Septiembre'
	 ,10 ,'Octubre'
	 ,11 , 'Noviembre'
	 ,12 , 'Diciembre', ' ')
|| ' de ' || TO_CHAR(SYSDATE, 'YYYY') AS FechaHoy,
	(select c.DS_Logo
  from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and
    uu.CD_Usuario = t)
   as Organismo
FROM
  REGISTROES ES, --REL_INTvsREs IR,
	AUX_OFICINAS OFI, AUX_PROVINCIAS PR,
	AUX_TIPOREGISTRO TRE, AUX_TIPOREGISTRO TRE1 ,
	AUX_TRANSPORTES TR,
	DAT_CONFIG CC, USUARIOS uuu,
	AUX_ORGANOS_PROD OG,
	AUX_ORGANOS_PROD OG1,
	AUX_DEPARTAMENTOS DEP,
	AUX_DEPARTAMENTOS DEP1

WHERE
	ES.Ejercicio = p
	AND ES.TipoES = q
	AND ES.Num_Registro = r
	--AND IR.Ejercicio=ES.Ejercicio
	--AND IR.TipoES=ES.TipoES
	--AND IR.Num_Registro=ES.Num_Registro
	AND ES.CD_Oficina = OFI.CD_Oficina
	AND (OFI.Provincia=PR.CD_Provincia(+))
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND ES.TipoES = TRE.CD_TipoRegistro
	AND (ES.CD_Div_Origen = OG.CD_Division(+))
	AND (ES.CD_Org_Origen = OG.CD_Organo(+))
	AND (ES.CD_Div_Destino = OG1.CD_Division(+))
	AND (ES.CD_Org_Destino = OG1.CD_Organo(+))
	AND (ES.Tipo_Regis_Original = TRE1.CD_TipoRegistro(+))
	AND (ES.CD_Org_Origen = DEP.CD_Organo(+))
	--AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_Org_Destino = DEP1.CD_Organo(+))
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND UUU.CD_USUARIO = t;
	--AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+));

  cursorOut:=ret_cursor;
END usp_RPT_FichaRegistro_2_MIN;


/*************************************************************************************************/
PROCEDURE usp_RPT_RegistroGeneral(p IN  INTEGER, q IN VARCHAR2,r IN VARCHAR2,
s IN INTEGER , cursorOut OUT t_cursor) IS
  ret_cursor t_cursor;

BEGIN
  OPEN ret_cursor FOR

      SELECT Num_RG AS Num_RG
      FROM REGISTROGENERAL
      WHERE Ejercicio = p AND
      	TipoES = q AND
      	CD_Oficina = r AND
      	Num_Registro = s;

  cursorOut:=ret_cursor;
END usp_RPT_RegistroGeneral;



/*************************************************************************************************/
/*  tiene llamada a cúspide*/
PROCEDURE usp_RPT_InformesConImagenes(p IN VARCHAR2,r IN  INTEGER,fv IN  DATE,
              ff IN DATE, cursorOut OUT t_cursor) IS
  ret_cursor t_cursor;

BEGIN
  OPEN ret_cursor FOR

SELECT
	fv AS fechai,
	ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha ,
	DECODE(ES.TipoES,'E', 'Entrada', 'S', 'Salida','') AS TipoES,
	ES.Resumen
FROM
	REGISTROES ES,
	AUX_OFICINAS OFI,
	REGISTRODOC DOC,
	REGISTROGENERAL RGEN

WHERE (ES.CD_Oficina = OFI.CD_Oficina AND OFI.CD_Oficina =  p)
      AND (ES.Ejercicio = DOC.Ejercicio AND ES.TipoES = DOC.TipoES AND DOC.Num_Registro = RGEN.Num_RG)
	  AND (RGEN.Ejercicio = ES.Ejercicio AND RGEN.TipoES = ES.TipoES AND
           RGEN.CD_Oficina = ES.CD_Oficina AND RGEN.Num_Registro = ES.Num_RegistroOF)
	  AND DOC.Tipo = 1
      AND ES.Fecha BETWEEN fv AND ff
	  AND (ES.Num_Registro = r OR r = 0)
	  --AND (ES.Num_Registro = r OR r IS NULL)
	  AND (fv IS NOT NULL OR ff IS NOT NULL)

ORDER BY 4, ES.TipoES DESC;

  cursorOut:=ret_cursor;
END usp_RPT_InformesConImagenes;


/*************************************************************************************************/
PROCEDURE usp_RPT_Interesados(cursorOut OUT t_cursor) IS
  ret_cursor t_cursor;

BEGIN
  OPEN ret_cursor FOR

    SELECT  I.*
    FROM    REL_INTVSRES I
    ORDER BY I.Ejercicio, I.TipoES, I.Num_Registro, I.CD_Interesado;

  cursorOut:=ret_cursor;
END usp_RPT_Interesados;



/*************************************************************************************************/
PROCEDURE usp_RPT_Correos(p IN  VARCHAR2,q IN DATE, r IN  DATE, s IN INTEGER,t IN INTEGER,
                                 u IN VARCHAR2, v IN VARCHAR2, cursorOut OUT t_cursor) IS
  ret_cursor t_cursor;

  fv  DATE := q;
  ff  DATE := r;
 /*
 en sql server
 if @s='' select @s =null
if @t='' select @t =null
--if @v='' select @v =null
declare @fv as datetime
declare @ff as datetime
--SET dateformat ymd
--select @fv=dateadd(day,@q,'1899-12-30')
--select @ff=dateadd(day,@r,'1899-12-30')
select @fv=@q
select @ff=@r
 */

BEGIN
  OPEN ret_cursor FOR

 SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
   (CASE WHEN CD_Organo_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES INT WHERE INT.Ejercicio = ES.Ejercicio AND
					INT.TipoES = ES.TipoES AND INT.Num_Registro = ES.Num_Registro
					GROUP BY INT.Ejercicio, INT.TipoES, INT.Num_Registro), ' ')
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG1.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES INT WHERE INT.Ejercicio = ES.Ejercicio AND
					INT.TipoES = ES.TipoES AND INT.Num_Registro = ES.Num_Registro
					GROUP BY INT.Ejercicio, INT.TipoES, INT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Organo_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES INT WHERE INT.Ejercicio = ES.Ejercicio AND
						INT.TipoES = ES.TipoES AND INT.Num_Registro = ES.Num_Registro
						GROUP BY INT.Ejercicio, INT.TipoES, INT.Num_Registro), ' ')
				ELSE  OG1.DS_Organo || ' (' || (SELECT AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG1.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES INT WHERE INT.Ejercicio = ES.Ejercicio AND
						INT.TipoES = ES.TipoES AND INT.Num_Registro = ES.Num_Registro
						GROUP BY INT.Ejercicio, INT.TipoES, INT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES INT WHERE INT.Ejercicio = ES.Ejercicio AND
				INT.TipoES = ES.TipoES AND INT.Num_Registro = ES.Num_Registro
				GROUP BY INT.Ejercicio, INT.TipoES, INT.Num_Registro)
		END
	END) AS OrgDestino,
		(CASE WHEN ES.CD_Organo_Destino IS NOT NULL THEN OG1.CP ELSE '' END) AS CPostal,
	ROWNUM AS "id"
--into #correos
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS OG1,
	AUX_DEPARTAMENTOS DEP1
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Organo_Destino=DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+))
	AND (ES.CD_Division_Destino=OG1.CD_Division(+))
	AND (ES.CD_Organo_Destino=OG1.CD_Organo(+))
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'S'
	AND ES.Fecha  BETWEEN fv AND ff
	AND (q IS NOT NULL OR r IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	--AND (ES.Num_RegistroOF >= s OR s is Null)
	--AND (ES.Num_RegistroOF <= t OR t is Null)
	AND ES.Estado IS NULL
	AND ES.Tipo_Transporte = u;

  cursorOut:=ret_cursor;
END usp_RPT_Correos;



/*************************************************************************************************/
PROCEDURE usp_RPT_IndicesE(p IN  VARCHAR2,
q IN INTEGER, --Fecha inicial
r IN INTEGER, --fecha final
s IN INTEGER, --registro inicial
t IN INTEGER, --registro final
u IN VARCHAR2  --organo destino
, cursorOut OUT t_cursor) IS

  ret_cursor t_cursor;

-- SET dateformat ymd
--select fv=dateadd(day,q,'1899-12-30')
--select ff=dateadd(day,r,'1899-12-30')

  fv  DATE := TO_DATE(q,'YYYY-MM-DD');
  ff  DATE := TO_DATE(r,'YYYY-MM-DD');

BEGIN
  OPEN ret_cursor FOR
SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
   (CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF) ||'/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END )AS NumRegistro,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha,
	DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
	(CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Organo_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Organo_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG1.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN CD_Organo_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo
				ELSE  OG1.DS_Organo || ' (' || (SELECT AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG1.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
	DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	ES.Num_Transporte,
	ES.Resumen,
	ES.ExpOferta,
	OFI.Direccion,
	OFI.CP,
	OFI.Poblacion,
	OFI.Telefono,
	OFI.FAX,
	ES.ExpOferta
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS OG,  AUX_ORGANOS OG1,
	AUX_TRANSPORTES TR, UsuariosV1 US,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, REGISTROESTRA EST
WHERE	EST.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Division_Origen=OG.CD_Division(+))
	AND (ES.CD_Organo_Origen=OG.CD_Organo(+))
	AND (ES.CD_Organo_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_Organo_Destino=DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+))
	AND (ES.CD_Division_Destino = OG1.CD_Division)
	AND (ES.CD_Organo_Destino = OG1.CD_Organo)
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarRegistro'
	AND (EST.F_Efectos BETWEEN fv  AND ff)
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	--AND (ES.Num_RegistroOF >= s OR s is Null)
	--AND (ES.Num_RegistroOF <= t OR t is Null)
	AND (ES.CD_Organo_Destino = u OR u is null)
	AND ES.Estado IS NULL
UNION
SELECT DISTINCT
	fv AS fechai, ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  ||'/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha,
	DECODE(ES.TipoES, 'E', 'Entrada', 'S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
	(CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Organo_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(SELECT AUX_OFICINAS.DS_Oficina FROM AUX_OFICINAS WHERE AUX_OFICINAS.CD_Oficina = EST.CD_Oficina) AS OrgDestino,
	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
	DECODE(ES.Tipo_Regis_Original, 'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	ES.Num_Transporte,
	ES.Resumen,
	ES.ExpOferta,
	OFI.Direccion,
	OFI.CP,
	OFI.Poblacion,
	OFI.Telefono,
	OFI.FAX,
	ES.ExpOferta
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS OG,  AUX_TRANSPORTES TR, UsuariosV1 US,
	AUX_DEPARTAMENTOS DEP, REGISTROESTRA EST
WHERE	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Division_Origen=OG.CD_Division(+))
	AND (ES.CD_Organo_Origen=OG.CD_Organo(+))
	AND ES.CD_Organo_Origen=DEP.CD_Organo(+)
	AND ES.CD_Dep_Origen=DEP.CD_Departamento(+)
	AND ES.Tipo_Transporte=TR.CD_Transporte(+)
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarUnidad'
    AND (EST.F_Efectos BETWEEN fv  AND ff)
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	--AND (ES.Num_RegistroOF >= s OR s is Null)
	--AND (ES.Num_RegistroOF <= t OR t is Null)
	AND (ES.CD_Organo_Destino IN
	 (SELECT CD_Organo FROM AUX_ORGANOS WHERE CD_Oficina =
	 (SELECT CD_Oficina FROM AUX_ORGANOS WHERE CD_Organo = u)) OR u is null)
	AND ES.Estado IS NULL
ORDER BY Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_IndicesE;



/*************************************************************************************************/
PROCEDURE usp_RPT_IndicesE2(p IN VARCHAR2,q IN INTEGER,
r IN INTEGER,
s IN  INTEGER,
t IN INTEGER,
u IN VARCHAR2
, cursorOut OUT t_cursor) IS

  ret_cursor t_cursor;
--SET dateformat ymd
--select @fv=dateadd(day,@q,'1899-12-30')
--select @ff=dateadd(day,@r,'1899-12-30')

  fv  DATE := TO_DATE(q,'YYYY-MM-DD');
  ff  DATE := TO_DATE(r,'YYYY-MM-DD');

BEGIN
  OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha,
	DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
    (CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Organo_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Organo_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG1.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN CD_Organo_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo
				ELSE  OG1.DS_Organo || ' (' || (SELECT AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG1.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
	DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	ES.Num_Transporte,
	ES.Resumen,
	ES.ExpOferta,
	OFI.Direccion,
	OFI.CP,
	OFI.Poblacion,
	OFI.Telefono,
	OFI.FAX
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS OG,  AUX_ORGANOS OG1,AUX_TRANSPORTES TR, UsuariosV1 US,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, REGISTROESTRA EST
WHERE
	EST.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Division_Origen=OG.CD_Division(+))
	AND (ES.CD_Organo_Origen=OG.CD_Organo(+))
	AND (ES.CD_Organo_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_Organo_Destino=DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+))
	AND (ES.CD_Division_Destino = OG1.CD_Division)
	AND (ES.CD_Organo_Destino = OG1.CD_Organo)
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarRegistro'
	AND (ES.FechaP BETWEEN fv AND ff)
	AND (q IS NOT NULL OR r IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	--AND (ES.Num_RegistroOF >= s OR s is Null)
	--AND (ES.Num_RegistroOF <= t OR t is Null)
	AND (ES.CD_Organo_Destino = u OR u = ' ')
	AND ES.Estado IS NULL
UNION
SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
    (CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END )AS NumRegistro,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha,
	CASE ES.TipoES
		WHEN 'E' THEN 'Entrada'
		WHEN 'S' THEN 'Salida'
		ELSE ''
	END AS TipoES,
	US.DS_Usuario,
	(CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Organo_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(SELECT AUX_OFICINAS.DS_Oficina FROM AUX_OFICINAS WHERE AUX_OFICINAS.CD_Oficina = EST.CD_Oficina) AS OrgDestino,
	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
	DECODE(ES.Tipo_Regis_Original, 'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	ES.Num_Transporte,
	ES.Resumen,
	ES.ExpOferta,
	OFI.Direccion,
	OFI.CP,
	OFI.Poblacion,
	OFI.Telefono,
	OFI.FAX
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS OG,  AUX_TRANSPORTES TR, UsuariosV1 US,
	AUX_DEPARTAMENTOS DEP, REGISTROESTRA EST
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Division_Origen=OG.CD_Division(+))
	AND (ES.CD_Organo_Origen=OG.CD_Organo(+))
	AND (ES.CD_Organo_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen =DEP.CD_Departamento(+))
	AND (ES.Tipo_Transporte =TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarUnidad'
	AND (ES.FechaP BETWEEN fv AND ff)
	AND (q IS NOT NULL OR r IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	--AND (ES.Num_RegistroOF >= s OR s is Null)
	--AND (ES.Num_RegistroOF <= t OR t is Null)
	AND (ES.CD_Organo_Destino IN
	    (SELECT CD_Organo FROM AUX_ORGANOS WHERE
		CD_Oficina = (SELECT CD_Oficina FROM AUX_ORGANOS WHERE CD_Organo = u)) OR u = ' ')
	AND ES.Estado IS NULL;

  cursorOut:=ret_cursor;
END usp_RPT_IndicesE2;



/*************************************************************************************************/
PROCEDURE usp_RPT_IndicesE2_fechas(p IN VARCHAR2,fv IN DATE,ff IN DATE, s IN INTEGER,
                               t IN INTEGER, u IN VARCHAR2 , cursorOut OUT t_cursor) IS

  ret_cursor t_cursor;

 BEGIN
  OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
    (CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha,
		DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
    (CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Organo_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Organo_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG1.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN CD_Organo_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo
				ELSE  OG1.DS_Organo || ' (' || (SELECT AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG1.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
		DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	ES.Num_Transporte,
	ES.Resumen,
	ES.ExpOferta,
	OFI.Direccion,
	OFI.CP,
	OFI.Poblacion,
	OFI.Telefono,
	OFI.FAX
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS OG,  AUX_ORGANOS OG1,AUX_TRANSPORTES TR, UsuariosV1 US,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, REGISTROESTRA EST
WHERE
	EST.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Division_Origen=OG.CD_Division(+))
	AND (ES.CD_Organo_Origen=OG.CD_Organo(+))
	AND (ES.CD_Organo_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_Organo_Destino=DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+)
	AND (ES.CD_Division_Destino = OG1.CD_Division))
	AND (ES.CD_Organo_Destino = OG1.CD_Organo)
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarRegistro'
	AND (TO_CHAR(EST.F_Efectos, 'DD/MM/YYYY')  BETWEEN TO_CHAR(fv, 'DD/MM/YYYY') AND TO_CHAR(ff, 'DD/MM/YYYY'))
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	--AND (ES.Num_RegistroOF >= s OR s is Null)
	--AND (ES.Num_RegistroOF <= t OR t is Null)
	AND (ES.CD_Organo_Destino = u OR u = ' ')
	AND ES.Estado IS NULL
UNION
SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
ES.Num_RegistroOF,
  (CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha,
	(CASE ES.TipoES
		WHEN 'E' THEN 'Entrada'
		WHEN 'S' THEN 'Salida'
		ELSE ''
	END) AS TipoES,
	US.DS_Usuario,
	(CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Organo_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(SELECT AUX_OFICINAS.DS_Oficina FROM AUX_OFICINAS WHERE AUX_OFICINAS.CD_Oficina = EST.CD_Oficina) AS OrgDestino,
	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
	(CASE  ES.Tipo_Regis_Original
		WHEN 'E' THEN ' / Entrada'
		WHEN 'S' THEN ' / Salida'
		ELSE ''
	END) AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	ES.Num_Transporte,
	ES.Resumen,
	ES.ExpOferta,
	OFI.Direccion,
	OFI.CP,
	OFI.Poblacion,
	OFI.Telefono,
	OFI.FAX
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS OG,  AUX_TRANSPORTES TR, UsuariosV1 US,
	AUX_DEPARTAMENTOS DEP, REGISTROESTRA EST
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Division_Origen=OG.CD_Division(+))
	AND (ES.CD_Organo_Origen=OG.CD_Organo(+))
	AND (ES.CD_Organo_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarUnidad'
	AND (TO_CHAR(EST.F_Efectos, 'DD/MM/YYYY')  BETWEEN TO_CHAR(fv, 'DD/MM/YYYY') AND TO_CHAR(ff, 'DD/MM/YYYY'))
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	--AND (ES.Num_RegistroOF >= s OR s is Null)
	--AND (ES.Num_RegistroOF <= t OR t is Null)
	AND (ES.CD_Organo_Destino IN
	(SELECT CD_Organo FROM AUX_ORGANOS WHERE CD_Oficina =
	(SELECT CD_Oficina FROM AUX_ORGANOS WHERE CD_Organo = u )) OR u = ' ')
	AND ES.Estado IS NULL

ORDER BY Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_IndicesE2_fechas;



/*************************************************************************************************/
-- Personalización del procedimiento usp_RPT_IndicesE2_fechas para el MIN
PROCEDURE usp_RPT_IndicesE2_fechas_MIN(p IN VARCHAR2, fv IN DATE, ff IN DATE, s IN INTEGER,
                               t IN INTEGER, u IN VARCHAR2, cursorOut OUT t_cursor)
IS

ret_cursor t_cursor;

BEGIN

OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
    (CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha,
	DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
  (CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_Organos_PROD
				WHERE OG.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
        AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN OG.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE OG.DS_Organo || ' (' ||
         (SELECT DS_Organo FROM AUX_Organos_PROD
					WHERE OG.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
          AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN DEP1.DS_Departamento || '.' || OG1.DS_Organo
			ELSE DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
       (SELECT DS_Organo FROM AUX_Organos_PROD
				WHERE OG1.CD_Organosup= AUX_Organos_PROD.CD_Organo(+)
         AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
			END
	ELSE
		CASE WHEN CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN OG1.DS_Organo
				ELSE OG1.DS_Organo || ' (' ||
         (SELECT DS_Organo FROM AUX_Organos_PROD
					WHERE OG1.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
          AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
	DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	ES.Num_Transporte,
	ES.Resumen,
	ES.ExpOferta,
	OFI.Direccion,
	OFI.CP,
	OFI.Poblacion,
	OFI.Telefono,
	OFI.FAX
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1,
  AUX_TRANSPORTES TR, UsuariosV1 US, AUX_DEPARTAMENTOS DEP,
  AUX_DEPARTAMENTOS DEP1, REGISTROESTRA EST
WHERE
	EST.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Div_Origen = OG.CD_Division(+))
	AND (ES.CD_Org_Origen = OG.CD_Organo(+))
	AND (ES.CD_Org_Origen = DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen = DEP.CD_Departamento(+))
	AND (ES.CD_Org_Destino = DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino = DEP1.CD_Departamento(+)
	AND (ES.CD_Div_Destino = OG1.CD_Division))
	AND (ES.CD_Org_Destino = OG1.CD_Organo)
	AND (ES.Tipo_Transporte = TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarRegistro'
	AND (TO_CHAR(EST.F_Efectos, 'DD/MM/YYYY')  BETWEEN TO_CHAR(fv, 'DD/MM/YYYY') AND TO_CHAR(ff, 'DD/MM/YYYY'))
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	--AND (ES.Num_RegistroOF >= s OR s is Null)
	--AND (ES.Num_RegistroOF <= t OR t is Null)
	AND (ES.CD_Org_Destino = u OR u = ' ')
	AND ES.Estado IS NULL
UNION
SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
  ES.Num_RegistroOF,
  (CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha,
	(CASE ES.TipoES
		WHEN 'E' THEN 'Entrada'
		WHEN 'S' THEN 'Salida'
		ELSE ''
	END) AS TipoES,
	US.DS_Usuario,
	(CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_Organos_PROD
				WHERE OG.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
        AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN OG.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG.DS_Organo || ' (' ||
         (SELECT DS_Organo FROM AUX_Organos_PROD
					WHERE OG.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
          AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(SELECT AUX_OFICINAS.DS_Oficina FROM AUX_OFICINAS WHERE AUX_OFICINAS.CD_Oficina = EST.CD_Oficina) AS OrgDestino,
	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
	(CASE  ES.Tipo_Regis_Original
		WHEN 'E' THEN ' / Entrada'
		WHEN 'S' THEN ' / Salida'
		ELSE ''
	END) AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	ES.Num_Transporte,
	ES.Resumen,
	ES.ExpOferta,
	OFI.Direccion,
	OFI.CP,
	OFI.Poblacion,
	OFI.Telefono,
	OFI.FAX
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_TRANSPORTES TR, UsuariosV1 US,
	AUX_DEPARTAMENTOS DEP, REGISTROESTRA EST
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Div_Origen = OG.CD_Division(+))
	AND (ES.CD_Org_Origen = OG.CD_Organo(+))
	AND (ES.CD_Org_Origen = DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen = DEP.CD_Departamento(+))
	AND (ES.Tipo_Transporte = TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarUnidad'
	AND (TO_CHAR(EST.F_Efectos, 'DD/MM/YYYY') BETWEEN TO_CHAR(fv, 'DD/MM/YYYY') AND TO_CHAR(ff, 'DD/MM/YYYY'))
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	--AND (ES.Num_RegistroOF >= s OR s is Null)
	--AND (ES.Num_RegistroOF <= t OR t is Null)
	AND (ES.CD_Org_Destino IN
	(SELECT CD_Organo FROM AUX_ORGANOS_PROD WHERE CD_Oficina =
	(SELECT CD_Oficina FROM AUX_ORGANOS_PROD WHERE CD_Organo = u )) OR u = ' ')
	AND ES.Estado IS NULL

ORDER BY Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_IndicesE2_fechas_MIN;


/*************************************************************************************************/
procedure usp_RPT_IndicesE_fechas(p         IN VARCHAR2,
                                  kk        IN VARCHAR2,
                                  cc        IN VARCHAR2,
                                  s         IN INTEGER,
                                  t         IN INTEGER,
                                  u         IN VARCHAR2,
                                  cursorOut OUT t_cursor) IS

  ret_cursor t_cursor;

  fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
  ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

BEGIN
  OPEN ret_cursor FOR

    SELECT DISTINCT fv AS fechai,
                    ff AS fechaf,
                    ES.Num_RegistroOF,
                    (CASE
                      WHEN (SELECT Num_RG
                              FROM REGISTROGENERAL RG
                             WHERE RG.Ejercicio = ES.Ejercicio
                               AND RG.TipoES = ES.TipoES
                               AND RG.CD_Oficina = ES.CD_Oficina
                               AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL THEN
                       --TO_CHAR(ES.Num_RegistroOF) || '/RG ' ||
                       TO_CHAR(ES.Num_RegistroOF) || NVL2(OFI.SIGLAOF,' (' || OFI.SIGLAOF || ') ','') || '/RG ' ||
                       TO_CHAR((SELECT Num_RG
                                 FROM REGISTROGENERAL RG
                                WHERE RG.Ejercicio = ES.Ejercicio
                                  AND RG.TipoES = ES.TipoES
                                  AND RG.CD_Oficina = ES.CD_Oficina
                                  AND RG.Num_Registro = ES.Num_RegistroOF))
                      ELSE
                       --TO_CHAR(ES.Num_RegistroOF)
                       TO_CHAR(ES.Num_RegistroOF) || NVL2(OFI.SIGLAOF,' (' || OFI.SIGLAOF || ') ','')
                    END) AS NumRegistro,
                    ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
                    ES.FECHA_REGIS_ORIGINAL AS Fecharem,
                    OFI.DS_Oficina,
                    ES.FechaP AS Fecha,
                    DECODE(ES.TipoES, 'E', 'Entrada', 'S', 'Salida', '') AS TipoES,
                    US.DS_Usuario,
                    (CASE
                      WHEN CD_Organo_Origen IS NOT NULL AND
                           CD_Dep_Origen IS NOT NULL THEN
                       CASE
                      WHEN OG.CD_Organosup IS NULL THEN
                       DEP.DS_Departamento || '.' || OG.DS_Organo ||
                       NVL((SELECT ' / ' || MAX(Nombre)
                             FROM REL_INTVSRES RINT
                            WHERE RINT.Ejercicio = ES.Ejercicio
                              AND RINT.TipoES = ES.TipoES
                              AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio,
                                     RINT.TipoES,
                                     RINT.Num_Registro),
                           ' ')
                      ELSE
                       DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
                       (SELECT AUX_OrganosV1.DS_Organo
                          FROM AUX_OrganosV1
                         WHERE OG.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')' ||
                       NVL((SELECT ' / ' || MAX(Nombre)
                             FROM REL_INTVSRES RINT
                            WHERE RINT.Ejercicio = ES.Ejercicio
                              AND RINT.TipoES = ES.TipoES
                              AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio,
                                     RINT.TipoES,
                                     RINT.Num_Registro),
                           ' ')
                    END ELSE CASE
                       WHEN CD_Organo_Origen IS NOT NULL THEN
                        CASE
                       WHEN OG.CD_Organosup IS NULL THEN
                        OG.DS_Organo ||
                        NVL((SELECT ' / ' || MAX(Nombre)
                              FROM REL_INTVSRES RINT
                             WHERE RINT.Ejercicio = ES.Ejercicio
                               AND RINT.TipoES = ES.TipoES
                               AND RINT.Num_Registro = ES.Num_Registro
                             GROUP BY RINT.Ejercicio,
                                      RINT.TipoES,
                                      RINT.Num_Registro),
                            ' ')
                       ELSE
                        OG.DS_Organo || ' (' ||
                        (SELECT AUX_OrganosV1.DS_Organo
                           FROM AUX_OrganosV1
                          WHERE OG.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')' ||
                        NVL((SELECT ' / ' || MAX(Nombre)
                              FROM REL_INTVSRES RINT
                             WHERE RINT.Ejercicio = ES.Ejercicio
                               AND RINT.TipoES = ES.TipoES
                               AND RINT.Num_Registro = ES.Num_Registro
                             GROUP BY RINT.Ejercicio,
                                      RINT.TipoES,
                                      RINT.Num_Registro),
                            ' ')
                     END ELSE (SELECT MAX(Nombre)
                                 FROM REL_INTVSRES RINT
                                WHERE RINT.Ejercicio = ES.Ejercicio
                                  AND RINT.TipoES = ES.TipoES
                                  AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio,
                                         RINT.TipoES,
                                         RINT.Num_Registro) END END) AS OrgOrigen,
                    CASE
                      WHEN (CD_Organo_Origen IS NULL AND
                           CD_Dep_Origen IS NULL) OR
                           (CD_Organo_Origen IS NOT NULL) THEN
                       (SELECT MAX(LOCALIDAD)
                          FROM REL_INTVSRES RINT
                         WHERE RINT.Ejercicio = ES.Ejercicio
                           AND RINT.TipoES = ES.TipoES
                           AND RINT.Num_Registro = ES.Num_Registro
                         GROUP BY RINT.Ejercicio,
                                  RINT.TipoES,
                                  RINT.Num_Registro)
                    END AS LOCALIDAD,
                    CASE
                      WHEN (CD_Organo_Origen IS NULL AND
                           CD_Dep_Origen IS NULL) OR
                           (CD_Organo_Origen IS NOT NULL) THEN
                       (SELECT MAX(PAIS)
                          FROM REL_INTVSRES RINT
                         WHERE RINT.Ejercicio = ES.Ejercicio
                           AND RINT.TipoES = ES.TipoES
                           AND RINT.Num_Registro = ES.Num_Registro
                         GROUP BY RINT.Ejercicio,
                                  RINT.TipoES,
                                  RINT.Num_Registro)
                    END AS PAIS,
                    CASE
                      WHEN (CD_Organo_Origen IS NULL AND
                           CD_Dep_Origen IS NULL) OR
                           (CD_Organo_Origen IS NOT NULL) THEN
                       (SELECT DS_PROVINCIA
                          FROM AUX_PROVINCIAS
                         WHERE CD_PROVINCIA =
                               (SELECT MAX(PROVINCIA)
                                  FROM REL_INTVSRES RINT
                                 WHERE RINT.Ejercicio = ES.Ejercicio
                                   AND RINT.TipoES = ES.TipoES
                                   AND RINT.Num_Registro = ES.Num_Registro
                                 GROUP BY RINT.Ejercicio,
                                          RINT.TipoES,
                                          RINT.Num_Registro))
                    END AS PROVINCIA,
                    ES.CD_Organo_Destino AS CodOrganoD,
                    (CASE
                      WHEN CD_Organo_Destino IS NOT NULL AND
                           CD_Dep_Destino IS NOT NULL THEN
                       CASE
                      WHEN OG1.CD_Organosup IS NULL THEN
                       DEP1.DS_Departamento || '.' || OG1.DS_Organo
                      ELSE
                       DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
                       (SELECT AUX_OrganosV1.DS_Organo
                          FROM AUX_OrganosV1
                         WHERE OG1.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')'
                    END ELSE CASE
                       WHEN CD_Organo_Destino IS NOT NULL THEN
                        CASE
                       WHEN OG1.CD_Organosup IS NULL THEN
                        OG1.DS_Organo
                       ELSE
                        OG1.DS_Organo || ' (' ||
                        (SELECT AUX_OrganosV1.DS_Organo
                           FROM AUX_OrganosV1
                          WHERE OG1.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')'
                     END ELSE (SELECT MAX(Nombre)
                                 FROM REL_INTVSRES RINT
                                WHERE RINT.Ejercicio = ES.Ejercicio
                                  AND RINT.TipoES = ES.TipoES
                                  AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio,
                                         RINT.TipoES,
                                         RINT.Num_Registro) END END) AS OrgDestino,
                    ' /' || ES.CD_Regis_Original AS CD_Regis_Original,
                    DECODE(ES.Tipo_Regis_Original,
                           'E',
                           ' / Entrada',
                           'S',
                           ' / Salida',
                           '') AS Tipo_Regis_original,

                    ES.Num_Regis_Original,
                    ES.Fecha_Regis_Original,
                    ES.Tipo_Transporte,
                    TR.DS_Transporte,
                    ES.Num_Transporte,
                    ES.Resumen,
                    ES.ExpOferta,
                    OFI.Direccion,
                    OFI.CP,
                    OFI.Poblacion,
                    OFI.Telefono,
                    OFI.FAX,
                    ES.ExpOferta
      FROM AUX_OFICINAS      OFI,
           REGISTROES        ES,
           AUX_ORGANOS       OG,
           AUX_ORGANOS       OG1,
           AUX_TRANSPORTES   TR,
           UsuariosV1        US,
           AUX_DEPARTAMENTOS DEP,
           AUX_DEPARTAMENTOS DEP1,
           REGISTROESTRA     EST
     WHERE EST.CD_Oficina = OFI.CD_Oficina
       AND (ES.CD_Division_Origen = OG.CD_Division(+))
       AND (ES.CD_Organo_Origen = OG.CD_Organo(+))
       AND ES.CD_Organo_Origen = DEP.CD_Organo(+)
       AND ES.CD_Dep_Origen = DEP.CD_Departamento(+)
       AND ES.CD_Organo_Destino = DEP1.CD_Organo(+)
       AND ES.CD_Dep_Destino = DEP1.CD_Departamento(+)
       AND (ES.CD_Division_Destino = OG1.CD_Division)
       AND (ES.CD_Organo_Destino = OG1.CD_Organo)
       AND ES.Tipo_Transporte = TR.CD_Transporte(+)
       AND ES.Usuario = US.DNI
       AND OFI.CD_Oficina = p
       AND ES.TipoES = 'E'
       AND ES.Ejercicio = EST.Ejercicio
       AND ES.TipoES = EST.TipoES
       AND ES.Num_Registro = EST.Num_Registro
       AND EST.CD_Tramite = 'EnviarRegistro'
       AND (trunc(EST.F_EFECTOS) >= fv and trunc(EST.F_EFECTOS) <= ff)
       AND (fv IS NOT NULL OR ff IS NOT NULL)
       AND (ES.Num_RegistroOF >= s OR s = 0)
       AND (ES.Num_RegistroOF <= t OR t = 0)
       AND ((ES.CD_Organo_Destino = u OR u is null) OR
           ((ES.CD_Organo_Destino IN
           (SELECT CD_Organo
                 FROM AUX_ORGANOS
                WHERE CD_Oficina =
                      (SELECT CD_Oficina FROM AUX_ORGANOS WHERE CD_Organo = u)) OR
           u is null)))
       AND ES.Estado IS NULL
    UNION
    SELECT DISTINCT fv AS fechai,
                    ff AS fechaf,
                    ES.Num_RegistroOF,
                    (CASE
                      WHEN (SELECT Num_RG
                              FROM REGISTROGENERAL RG
                             WHERE RG.Ejercicio = ES.Ejercicio
                               AND RG.TipoES = ES.TipoES
                               AND RG.CD_Oficina = ES.CD_Oficina
                               AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL THEN
                       --TO_CHAR(ES.Num_RegistroOF) || '/RG ' ||
                       TO_CHAR(ES.Num_RegistroOF) || NVL2(OFI.SIGLAOF,' (' || OFI.SIGLAOF || ') ','') || '/RG ' ||
                       TO_CHAR((SELECT Num_RG
                                 FROM REGISTROGENERAL RG
                                WHERE RG.Ejercicio = ES.Ejercicio
                                  AND RG.TipoES = ES.TipoES
                                  AND RG.CD_Oficina = ES.CD_Oficina
                                  AND RG.Num_Registro = ES.Num_RegistroOF))
                      ELSE
                       --TO_CHAR(ES.Num_RegistroOF)
                       TO_CHAR(ES.Num_RegistroOF) || NVL2(OFI.SIGLAOF,' (' || OFI.SIGLAOF || ') ','')
                    END) AS NumRegistro,
                    ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
                    ES.FECHA_REGIS_ORIGINAL AS Fecharem,
                    OFI.DS_Oficina,
                    ES.FechaP AS Fecha,
                    DECODE(ES.TipoES, 'E', 'Entrada', 'S', 'Salida', '') AS TipoES,
                    US.DS_Usuario,
                    (CASE
                      WHEN CD_Organo_Origen IS NOT NULL AND
                           CD_Dep_Origen IS NOT NULL THEN
                       CASE
                      WHEN OG.CD_Organosup IS NULL THEN
                       DEP.DS_Departamento || '.' || OG.DS_Organo ||
                       NVL((SELECT ' / ' || MAX(Nombre)
                             FROM REL_INTVSRES RINT
                            WHERE RINT.Ejercicio = ES.Ejercicio
                              AND RINT.TipoES = ES.TipoES
                              AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio,
                                     RINT.TipoES,
                                     RINT.Num_Registro),
                           ' ')
                      ELSE
                       DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
                       (SELECT AUX_OrganosV1.DS_Organo
                          FROM AUX_OrganosV1
                         WHERE OG.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')' ||
                       NVL((SELECT ' / ' || MAX(Nombre)
                             FROM REL_INTVSRES RINT
                            WHERE RINT.Ejercicio = ES.Ejercicio
                              AND RINT.TipoES = ES.TipoES
                              AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio,
                                     RINT.TipoES,
                                     RINT.Num_Registro),
                           ' ')
                    END ELSE CASE
                       WHEN CD_Organo_Origen IS NOT NULL THEN
                        CASE
                       WHEN OG.CD_Organosup IS NULL THEN
                        OG.DS_Organo ||
                        NVL((SELECT ' / ' || MAX(Nombre)
                              FROM REL_INTVSRES RINT
                             WHERE RINT.Ejercicio = ES.Ejercicio
                               AND RINT.TipoES = ES.TipoES
                               AND RINT.Num_Registro = ES.Num_Registro
                             GROUP BY RINT.Ejercicio,
                                      RINT.TipoES,
                                      RINT.Num_Registro),
                            ' ')
                       ELSE
                        OG.DS_Organo || ' (' ||
                        (SELECT AUX_OrganosV1.DS_Organo
                           FROM AUX_OrganosV1
                          WHERE OG.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')' ||
                        NVL((SELECT ' / ' || MAX(Nombre)
                              FROM REL_INTVSRES RINT
                             WHERE RINT.Ejercicio = ES.Ejercicio
                               AND RINT.TipoES = ES.TipoES
                               AND RINT.Num_Registro = ES.Num_Registro
                             GROUP BY RINT.Ejercicio,
                                      RINT.TipoES,
                                      RINT.Num_Registro),
                            ' ')
                     END ELSE (SELECT MAX(Nombre)
                                 FROM REL_INTVSRES RINT
                                WHERE RINT.Ejercicio = ES.Ejercicio
                                  AND RINT.TipoES = ES.TipoES
                                  AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio,
                                         RINT.TipoES,
                                         RINT.Num_Registro) END END) AS OrgOrigen,
                    CASE
                      WHEN (CD_Organo_Origen IS NULL AND
                           CD_Dep_Origen IS NULL) OR
                           (CD_Organo_Origen IS NOT NULL) THEN
                       (SELECT MAX(LOCALIDAD)
                          FROM REL_INTVSRES RINT
                         WHERE RINT.Ejercicio = ES.Ejercicio
                           AND RINT.TipoES = ES.TipoES
                           AND RINT.Num_Registro = ES.Num_Registro
                         GROUP BY RINT.Ejercicio,
                                  RINT.TipoES,
                                  RINT.Num_Registro)
                    END AS LOCALIDAD,
                    CASE
                      WHEN (CD_Organo_Origen IS NULL AND
                           CD_Dep_Origen IS NULL) OR
                           (CD_Organo_Origen IS NOT NULL) THEN
                       (SELECT MAX(PAIS)
                          FROM REL_INTVSRES RINT
                         WHERE RINT.Ejercicio = ES.Ejercicio
                           AND RINT.TipoES = ES.TipoES
                           AND RINT.Num_Registro = ES.Num_Registro
                         GROUP BY RINT.Ejercicio,
                                  RINT.TipoES,
                                  RINT.Num_Registro)
                    END AS PAIS,
                    CASE
                      WHEN (CD_Organo_Origen IS NULL AND
                           CD_Dep_Origen IS NULL) OR
                           (CD_Organo_Origen IS NOT NULL) THEN
                       (SELECT DS_PROVINCIA
                          FROM AUX_PROVINCIAS
                         WHERE CD_PROVINCIA =
                               (SELECT MAX(PROVINCIA)
                                  FROM REL_INTVSRES RINT
                                 WHERE RINT.Ejercicio = ES.Ejercicio
                                   AND RINT.TipoES = ES.TipoES
                                   AND RINT.Num_Registro = ES.Num_Registro
                                 GROUP BY RINT.Ejercicio,
                                          RINT.TipoES,
                                          RINT.Num_Registro))
                    END AS PROVINCIA,
                    EST.CD_Oficina AS CodOrganoD,
                    (SELECT AUX_OFICINAS.DS_Oficina
                       FROM AUX_OFICINAS
                      WHERE AUX_OFICINAS.CD_Oficina = EST.CD_Oficina) AS OrgDestino,
                    ' /' || ES.CD_Regis_Original AS CD_Regis_Original,
                    -- oficina(EST.CD_Oficina) as OrgDestino,
                    --' /' || ES.CD_Regis_Original AS CD_Regis_Original,
                    DECODE(ES.Tipo_Regis_Original,
                           'E',
                           ' / Entrada',
                           'S',
                           ' / Salida',
                           '') AS Tipo_Regis_original,
                    ES.Num_Regis_Original,
                    ES.Fecha_Regis_Original,
                    ES.Tipo_Transporte,
                    TR.DS_Transporte,
                    ES.Num_Transporte,
                    ES.Resumen,
                    ES.ExpOferta,
                    OFI.Direccion,
                    OFI.CP,
                    OFI.Poblacion,
                    OFI.Telefono,
                    OFI.FAX,
                    ES.ExpOferta
      FROM AUX_OFICINAS      OFI,
           REGISTROES        ES,
           AUX_ORGANOS       OG,
           AUX_TRANSPORTES   TR,
           UsuariosV1        US,
           AUX_DEPARTAMENTOS DEP,
           REGISTROESTRA     EST
     WHERE ES.CD_Oficina = OFI.CD_Oficina
       AND (ES.CD_Division_Origen = OG.CD_Division(+))
       AND (ES.CD_Organo_Origen = OG.CD_Organo(+))
       AND (ES.CD_Organo_Origen = DEP.CD_Organo(+))
       AND (ES.CD_Dep_Origen = DEP.CD_Departamento(+))
       AND (ES.Tipo_Transporte = TR.CD_Transporte(+))
       AND ES.Usuario = US.DNI
       AND OFI.CD_Oficina = p
       AND ES.TipoES = 'E'
       AND ES.Ejercicio = EST.Ejercicio
       AND ES.TipoES = EST.TipoES
       AND ES.Num_Registro = EST.Num_Registro
       AND EST.CD_Tramite = 'EnviarUnidad'
       AND (TRUNC(EST.F_EFECTOS) >= fv and trunc(EST.F_EFECTOS) <= ff)
       AND (fv IS NOT NULL OR ff IS NOT NULL)
       AND (ES.Num_RegistroOF >= s OR s = 0)
       AND (ES.Num_RegistroOF <= t OR t = 0)
       AND ((ES.CD_Organo_Destino = u OR u is null) OR
           ((ES.CD_Organo_Destino IN
           (SELECT CD_Organo
                 FROM AUX_ORGANOS
                WHERE CD_Oficina =
                      (SELECT CD_Oficina FROM AUX_ORGANOS WHERE CD_Organo = u)) OR
           u is null)))
       AND ES.Estado IS NULL

     ORDER BY OrgDestino, Num_RegistroOF;

  cursorOut := ret_cursor;
END usp_RPT_IndicesE_fechas;


/*************************************************************************************************/
-- Personalización del procedimiento usp_RPT_IndicesE_fechas para el MIN
PROCEDURE usp_RPT_IndicesE3_fechas_MIN(p IN VARCHAR2, kk IN VARCHAR2, cc IN VARCHAR2,
  s IN INTEGER, t IN INTEGER, u IN VARCHAR2, cursorOut OUT t_cursor)
IS

ret_cursor t_cursor;
fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

BEGIN

OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		    TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha,
	DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
	(CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_Organos_PROD
				WHERE OG.CD_Organosup= AUX_Organos_PROD.CD_Organo(+)
        AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG.DS_Organo || ' (' ||
        (SELECT DS_Organo FROM AUX_Organos_PROD
					WHERE OG.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
          AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,

	CASE WHEN (ES.CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (ES.CD_Org_Origen IS NOT NULL)
	THEN
	(SELECT MAX(LOCALIDAD) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS LOCALIDAD,

	CASE WHEN (ES.CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (ES.CD_Org_Origen IS NOT NULL)
	THEN
	(SELECT MAX(PAIS) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS PAIS,

	CASE WHEN (ES.CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (ES.CD_Org_Origen IS NOT NULL)
	THEN
		(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	END AS PROVINCIA,

	ES.CD_Org_Destino AS CodOrganoD,

	(CASE WHEN ES.CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_Organos_PROD
				WHERE OG1.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
        AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')'
			END
	ELSE
		CASE WHEN ES.CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN OG1.DS_Organo
				ELSE OG1.DS_Organo || ' (' ||
        (SELECT DS_Organo FROM AUX_Organos_PROD
				 WHERE OG1.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
         AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,

	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
	DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	ES.Num_Transporte,
	ES.Resumen,
	ES.ExpOferta,
	OFI.Direccion,
	OFI.CP,
	OFI.Poblacion,
	OFI.Telefono,
	OFI.FAX,
	ES.ExpOferta
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1,
  AUX_TRANSPORTES TR, UsuariosV1 US, AUX_DEPARTAMENTOS DEP,
  AUX_DEPARTAMENTOS DEP1, REGISTROESTRA EST
WHERE
	EST.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Div_Origen = OG.CD_Division(+))
	AND (ES.CD_Org_Origen = OG.CD_Organo(+))
	AND ES.CD_Org_Origen = DEP.CD_Organo(+)
	AND ES.CD_Dep_Origen = DEP.CD_Departamento(+)
	AND ES.CD_Org_Destino = DEP1.CD_Organo(+)
	AND ES.CD_Dep_Destino = DEP1.CD_Departamento(+)
	AND (ES.CD_Div_Destino = OG1.CD_Division)
	AND (ES.CD_Org_Destino = OG1.CD_Organo)
	AND ES.Tipo_Transporte = TR.CD_Transporte(+)
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarRegistro'
	AND (trunc(EST.F_EFECTOS) >= fv and trunc(EST.F_EFECTOS) <= ff)
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	AND ((ES.CD_Org_Destino = u OR u is null)
		 OR (
	 (ES.CD_Org_Destino IN
	 (SELECT CD_Organo FROM AUX_ORGANOS_PROD WHERE CD_Oficina =
	  (SELECT CD_Oficina FROM AUX_ORGANOS_PROD WHERE CD_Organo = u)) OR u is null)))
	AND ES.Estado IS NULL
UNION
SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha,
	DECODE(ES.TipoES, 'E', 'Entrada', 'S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
	(CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN DEP.DS_Departamento || '.' || OG.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
       (SELECT DS_Organo FROM AUX_Organos_PROD
				WHERE OG.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
        AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE OG.DS_Organo || ' (' ||
        (SELECT DS_Organo FROM AUX_Organos_PROD
				 WHERE OG.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
          AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,

	CASE WHEN (ES.CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (ES.CD_Org_Origen IS NOT NULL)
	THEN
	(SELECT MAX(LOCALIDAD) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS LOCALIDAD,

	CASE WHEN (ES.CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (ES.CD_Org_Origen IS NOT NULL)
	THEN
	(SELECT MAX(PAIS) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS PAIS,

	CASE WHEN (ES.CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (ES.CD_Org_Origen IS NOT NULL)
	THEN
		(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	END AS PROVINCIA,

	EST.CD_Oficina AS CodOrganoD,

	(SELECT AUX_OFICINAS.DS_Oficina FROM AUX_OFICINAS WHERE AUX_OFICINAS.CD_Oficina = EST.CD_Oficina) AS OrgDestino,
	' /' ||ES.CD_Regis_Original AS CD_Regis_Original,
	-- oficina(EST.CD_Oficina) as OrgDestino,
	--' /' || ES.CD_Regis_Original AS CD_Regis_Original,
	DECODE(ES.Tipo_Regis_Original, 'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	ES.Num_Transporte,
	ES.Resumen,
	ES.ExpOferta,
	OFI.Direccion,
	OFI.CP,
	OFI.Poblacion,
	OFI.Telefono,
	OFI.FAX,
	ES.ExpOferta
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_TRANSPORTES TR,
  UsuariosV1 US, AUX_DEPARTAMENTOS DEP, REGISTROESTRA EST
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Div_Origen = OG.CD_Division(+))
	AND (ES.CD_Org_Origen = OG.CD_Organo(+))
	AND (ES.CD_Org_Origen = DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen = DEP.CD_Departamento(+))
	AND (ES.Tipo_Transporte = TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarUnidad'
	AND (TRUNC(EST.F_EFECTOS) >= fv and trunc(EST.F_EFECTOS) <= ff)
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	AND ((ES.CD_Org_Destino = u OR u is null)
		OR (
	 (ES.CD_Org_Destino IN
	 (SELECT CD_Organo FROM AUX_ORGANOS_PROD WHERE CD_Oficina =
	  (SELECT CD_Oficina FROM AUX_ORGANOS_PROD WHERE CD_Organo = u)) OR u is null)))
		AND ES.Estado IS NULL

ORDER BY  OrgDestino, Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_IndicesE3_fechas_MIN;


/*************************************************************************************************/
PROCEDURE usp_RPT_IndicesE_fechas_2(p IN VARCHAR2, kk IN VARCHAR2, cc IN VARCHAR2,
  s IN INTEGER, t IN INTEGER, u IN VARCHAR2 , v IN VARCHAR2 ,cursorOut OUT t_cursor) IS

  ret_cursor t_cursor;

fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

 BEGIN
  OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		    TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha,
	DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
	(CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Organo_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	CASE WHEN (CD_Organo_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
	(SELECT MAX(LOCALIDAD)  FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS LOCALIDAD,
	CASE WHEN  (CD_Organo_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
	(SELECT MAX(PAIS)  FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS PAIS,
	CASE WHEN  (CD_Organo_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
		(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	END AS PROVINCIA,
	ES.CD_Organo_Destino AS CodOrganoD,
	(CASE WHEN CD_Organo_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG1.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN CD_Organo_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo
				ELSE  OG1.DS_Organo || ' (' || (SELECT AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG1.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
		DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,

	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	ES.Num_Transporte,
	ES.Resumen,
	ES.ExpOferta,
	OFI.Direccion,
	OFI.CP,
	OFI.Poblacion,
	OFI.Telefono,
	OFI.FAX,
	ES.ExpOferta,
		(select c.DS_Logo
  from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and
    uu.CD_Usuario = v)
   as Organismo
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS OG,  AUX_ORGANOS OG1,AUX_TRANSPORTES TR, UsuariosV1 US,
	DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, REGISTROESTRA EST
WHERE
	--EST.CD_Oficina = OFI.CD_Oficina
  ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Division_Origen=OG.CD_Division(+))
	AND (ES.CD_Organo_Origen=OG.CD_Organo(+))
	AND ES.CD_Organo_Origen=DEP.CD_Organo(+)
	AND ES.CD_Dep_Origen=DEP.CD_Departamento(+)
	AND ES.CD_Organo_Destino=DEP1.CD_Organo(+)
	AND ES.CD_Dep_Destino=DEP1.CD_Departamento(+)
	AND (ES.CD_Division_Destino = OG1.CD_Division)
	AND (ES.CD_Organo_Destino = OG1.CD_Organo)
	AND ES.Tipo_Transporte=TR.CD_Transporte(+)
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarRegistro'
	AND (trunc(EST.F_EFECTOS) >= fv and trunc(EST.F_EFECTOS) <= ff)
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	AND ((ES.CD_Organo_Destino  = u OR u is null)
		 OR (
	 (ES.CD_Organo_Destino IN
	 (SELECT CD_Organo FROM AUX_ORGANOS WHERE CD_Oficina =
	  (SELECT CD_Oficina FROM AUX_ORGANOS WHERE CD_Organo = u)) OR u is null)
	  ))
	    AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND UUU.CD_USUARIO = v
	AND ES.Estado IS NULL
UNION
SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha,
	DECODE(ES.TipoES, 'E', 'Entrada', 'S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
	(CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Organo_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	CASE WHEN (CD_Organo_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
	(SELECT MAX(LOCALIDAD)  FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS LOCALIDAD,
	CASE WHEN  (CD_Organo_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
	(SELECT MAX(PAIS)  FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS PAIS,
	CASE WHEN  (CD_Organo_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
		(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	END AS PROVINCIA,
	EST.CD_Oficina AS CodOrganoD,
	(SELECT AUX_OFICINAS.DS_Oficina FROM AUX_OFICINAS WHERE AUX_OFICINAS.CD_Oficina = EST.CD_Oficina) AS OrgDestino,
	' /' ||ES.CD_Regis_Original AS CD_Regis_Original,
	-- oficina(EST.CD_Oficina) as OrgDestino,
	--' /' || ES.CD_Regis_Original AS CD_Regis_Original,
	DECODE(ES.Tipo_Regis_Original, 'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	ES.Num_Transporte,
	ES.Resumen,
	ES.ExpOferta,
	OFI.Direccion,
	OFI.CP,
	OFI.Poblacion,
	OFI.Telefono,
	OFI.FAX,
	ES.ExpOferta,
	(select c.DS_Logo
  from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and
    uu.CD_Usuario = v)
   as Organismo
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS OG,  AUX_TRANSPORTES TR, UsuariosV1 US,
	DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, REGISTROESTRA EST
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Division_Origen=OG.CD_Division(+))
	AND (ES.CD_Organo_Origen=OG.CD_Organo(+))
	AND (ES.CD_Organo_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarUnidad'
	AND (TRUNC(EST.F_EFECTOS) >= fv and trunc(EST.F_EFECTOS) <= ff)
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	AND ((ES.CD_Organo_Destino  = u OR u is null)
		OR (
	 (ES.CD_Organo_Destino IN
	 (SELECT CD_Organo FROM AUX_ORGANOS WHERE CD_Oficina =
	  (SELECT CD_Oficina FROM AUX_ORGANOS WHERE CD_Organo = u)) OR u is null)
	  ))
	  AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND UUU.CD_USUARIO = v
 	AND ES.Estado IS NULL

ORDER BY  OrgDestino, Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_IndicesE_fechas_2;



/*************************************************************************************************/
-- Modificado Incidencia 7810  -- 26/12/2006
PROCEDURE usp_RPT_IndicesE_fechas_MIN(p IN VARCHAR2, kk IN VARCHAR2, cc IN VARCHAR2,
                                      s IN INTEGER, t IN INTEGER, u IN VARCHAR2,
                                      v IN VARCHAR2, w IN INTEGER, x IN INTEGER, y IN INTEGER, cursorOut OUT t_cursor)
IS
ret_cursor t_cursor;

fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

BEGIN

  OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE
   WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				        RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
	  THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		    TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
        				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				        AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
	END) AS NumRegistro,
	--ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	--ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha,
	--DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
  ----
  (SELECT DS_PROVINCIA FROM AUX_PROVINCIAS
   WHERE CD_PROVINCIA = ES.CD_PROV_ORIGEN) || ' ' ||
  NVL((SELECT ' / ' || DS_LOCALIDAD FROM AUX_LOCALIDADES
   WHERE CD_PROVINCIA = ES.CD_PROV_ORIGEN AND CD_LOCALIDAD = ES.CD_LOC_ORIGEN),' ') AS ProvLocOrigen,
  ----
	(CASE
   WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
     THEN CASE
            WHEN OG.CD_Organosup IS NULL
			        THEN DEP.DS_Departamento || '.' || OG.DS_Organo ||
                   NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                   			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
             	ELSE DEP.DS_Departamento || '.' || OG.DS_Organo ||
                    ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                             WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                              AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                   NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                   			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			    END
     ELSE
     		  CASE
            WHEN ES.CD_Org_Origen IS NOT NULL
            	THEN
                CASE WHEN OG.CD_Organosup IS NULL
                	THEN OG.DS_Organo ||
                  		 NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                       		  RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                  ELSE OG.DS_Organo || ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                                WHERE	OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                                                 AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                       NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                       			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				          END
		          ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
              			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		      END
	   END) AS OrgOrigen,
	--CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	--THEN
	--(SELECT MAX(LOCALIDAD) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
	--			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
	--			GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
	--	END AS LOCALIDAD,
	--CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	--THEN
	--(SELECT MAX(PAIS) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
	--			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
	--			GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
	--	END AS PAIS,
	--CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	--THEN
	--	(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	--  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
	--			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
	--			GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	--END AS PROVINCIA,
	ES.CD_Org_Destino AS CodOrganoD,
  ----
  (SELECT DS_PROVINCIA FROM AUX_PROVINCIAS
   WHERE CD_PROVINCIA = ES.CD_PROV_DESTINO) AS ProvDestino,
  (SELECT DS_LOCALIDAD FROM AUX_LOCALIDADES
   WHERE CD_PROVINCIA = ES.CD_PROV_DESTINO AND CD_LOCALIDAD = ES.CD_LOC_DESTINO) AS LocDestino,
  (SELECT DS_DIVISION FROM AUX_DIVISIONES
   WHERE CD_DIVISION = ES.CD_DIV_DESTINO) AS DivDestino,

	(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN DEP1.DS_Departamento || '.' || OG1.DS_Organo
			ELSE DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
       (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
         AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
			END
	ELSE
		CASE WHEN CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN OG1.DS_Organo
				ELSE OG1.DS_Organo || ' (' ||
         (SELECT DS_Organo FROM AUX_ORGANOS_PROD
					WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
          AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrganoDestino,

  ----
  (SELECT DS_PROVINCIA FROM AUX_PROVINCIAS
   WHERE CD_PROVINCIA = ES.CD_PROV_DESTINO) || ' ' ||
  (SELECT DS_LOCALIDAD FROM AUX_LOCALIDADES
   WHERE CD_PROVINCIA = ES.CD_PROV_DESTINO AND CD_LOCALIDAD = ES.CD_LOC_DESTINO) || ' ' ||
  (SELECT DS_DIVISION FROM AUX_DIVISIONES
   WHERE CD_DIVISION = ES.CD_DIV_DESTINO) || ' ' ||
  ----
	(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN DEP1.DS_Departamento || '.' || OG1.DS_Organo
			ELSE DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
       (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
         AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
			END
	ELSE
		CASE WHEN CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN OG1.DS_Organo
				ELSE OG1.DS_Organo || ' (' ||
         (SELECT DS_Organo FROM AUX_ORGANOS_PROD
					WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
          AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
	--DECODE(ES.Tipo_Regis_Original,'E',' / Entrada','S',' / Salida','') AS Tipo_Regis_original,
	--ES.Num_Regis_Original,
	--ES.Fecha_Regis_Original,
	--ES.Tipo_Transporte,
	--TR.DS_Transporte,
	--ES.Num_Transporte,
	ES.Resumen,
	--ES.ExpOferta,
	--OFI.Direccion,
	--OFI.CP,
	--OFI.Poblacion,
	--OFI.Telefono,
	--OFI.FAX,
	(select c.DS_Logo
   from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = v)
   as Organismo
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1,
  -- AUX_TRANSPORTES TR,
  UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, REGISTROESTRA EST
WHERE
	EST.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Div_Origen=OG.CD_Division(+))
	AND (ES.CD_Org_Origen=OG.CD_Organo(+))
	AND ES.CD_Org_Origen=DEP.CD_Organo(+)
	AND ES.CD_Dep_Origen=DEP.CD_Departamento(+)
	AND ES.CD_Org_Destino=DEP1.CD_Organo(+)
	AND ES.CD_Dep_Destino=DEP1.CD_Departamento(+)
	AND (ES.CD_Div_Destino = OG1.CD_Division)
	AND (ES.CD_Org_Destino = OG1.CD_Organo)
	--AND ES.Tipo_Transporte=TR.CD_Transporte(+)
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarRegistro'
	AND (trunc(EST.F_EFECTOS) >= fv and trunc(EST.F_EFECTOS) <= ff)
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	AND ( (ES.CD_Org_Destino  = u OR u is null)
		    OR
         --Incidencia Produccion
        --((ES.CD_Org_Destino IN
        --	 (SELECT CD_Organo FROM AUX_ORGANOS_PROD WHERE CD_Oficina =
        --   	  (SELECT CD_Oficina FROM AUX_ORGANOS_PROD WHERE CD_Organo = u)) OR u is null)))
        ((ES.CD_Org_Destino IN
        	 (SELECT CD_Organo FROM AUX_ORGANOS_PROD WHERE CD_Oficina =
          	  (SELECT CD_Oficina FROM AUX_ORGANOS_PROD WHERE CD_Organo = u and CD_Division = w)) OR u is null)))

  ----
  AND (ES.CD_DIV_DESTINO = w OR w = 0)
  AND (ES.CD_PROV_DESTINO = x OR x = 0)
  AND (ES.CD_LOC_DESTINO = y OR y = 0)
  ----
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND UUU.CD_USUARIO = v
	AND ES.Estado IS NULL
UNION
SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	--ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	--ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha,
	--DECODE(ES.TipoES, 'E', 'Entrada', 'S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
  ----
  (SELECT DS_PROVINCIA FROM AUX_PROVINCIAS
   WHERE CD_PROVINCIA = ES.CD_PROV_ORIGEN) || ' ' ||
  NVL((SELECT ' / ' || DS_LOCALIDAD FROM AUX_LOCALIDADES
   WHERE CD_PROVINCIA = ES.CD_PROV_ORIGEN AND CD_LOCALIDAD = ES.CD_LOC_ORIGEN),' ') AS ProvLocOrigen,
  ----
	(CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
         AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG.DS_Organo || ' (' ||
        (SELECT DS_Organo FROM AUX_ORGANOS_PROD
					WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
          AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	--CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	--THEN
	--(SELECT MAX(LOCALIDAD) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
	--			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
	--			GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
	--	END AS LOCALIDAD,
	--CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	--THEN
	--(SELECT MAX(PAIS) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
	--			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
	--			GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
	--	END AS PAIS,
	--CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	--THEN
	--	(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	--  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
	--			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
	--			GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	--END AS PROVINCIA,
	EST.CD_Oficina AS CodOrganoD,
  ----
  (SELECT DS_PROVINCIA FROM AUX_PROVINCIAS
   WHERE CD_PROVINCIA = ES.CD_PROV_DESTINO) AS ProvDestino,
  (SELECT DS_LOCALIDAD FROM AUX_LOCALIDADES
   WHERE CD_PROVINCIA = ES.CD_PROV_DESTINO AND CD_LOCALIDAD = ES.CD_LOC_DESTINO) AS LocDestino,
  (SELECT DS_DIVISION FROM AUX_DIVISIONES
   WHERE CD_DIVISION = ES.CD_DIV_DESTINO) AS DivDestino,
	(SELECT AUX_OFICINAS.DS_Oficina FROM AUX_OFICINAS
   WHERE AUX_OFICINAS.CD_Oficina = EST.CD_Oficina) AS OrganoDestino,
  ----
  (SELECT DS_PROVINCIA FROM AUX_PROVINCIAS
   WHERE CD_PROVINCIA = ES.CD_PROV_DESTINO) || ' ' ||
  (SELECT DS_LOCALIDAD FROM AUX_LOCALIDADES
   WHERE CD_PROVINCIA = ES.CD_PROV_DESTINO AND CD_LOCALIDAD = ES.CD_LOC_DESTINO) || ' ' ||
  (SELECT DS_DIVISION FROM AUX_DIVISIONES
   WHERE CD_DIVISION = ES.CD_DIV_DESTINO) || ' ' ||
  ----
	(SELECT AUX_OFICINAS.DS_Oficina FROM AUX_OFICINAS WHERE AUX_OFICINAS.CD_Oficina = EST.CD_Oficina) AS OrgDestino,
	' /' ||ES.CD_Regis_Original AS CD_Regis_Original,
	--DECODE(ES.Tipo_Regis_Original,'E',' / Entrada','S',' / Salida','') AS Tipo_Regis_original,
	--ES.Num_Regis_Original,
	--ES.Fecha_Regis_Original,
	--ES.Tipo_Transporte,
	--TR.DS_Transporte,
	--ES.Num_Transporte,
	ES.Resumen,
	--ES.ExpOferta,
	--OFI.Direccion,
	--OFI.CP,
	--OFI.Poblacion,
	--OFI.Telefono,
	--OFI.FAX,
	(select c.DS_Logo
   from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = v)
   as Organismo
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS_PROD OG,
  --AUX_TRANSPORTES TR,
  UsuariosV1 US,	DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, REGISTROESTRA EST
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Div_Origen=OG.CD_Division(+))
	AND (ES.CD_Org_Origen=OG.CD_Organo(+))
	AND (ES.CD_Org_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	--AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarUnidad'
	AND (TRUNC(EST.F_EFECTOS) >= fv and trunc(EST.F_EFECTOS) <= ff)
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	AND ( (ES.CD_Org_Destino  = u OR u is null)
		    OR
        -- Incidencia Produccion
        --((ES.CD_Org_Destino IN
        --	 (SELECT CD_Organo FROM AUX_ORGANOS_PROD WHERE CD_Oficina =
        --   	  (SELECT CD_Oficina FROM AUX_ORGANOS_PROD WHERE CD_Organo = u)) OR u is null)))
        ((ES.CD_Org_Destino IN
        	 (SELECT CD_Organo FROM AUX_ORGANOS_PROD WHERE CD_Oficina =
           	  (SELECT CD_Oficina FROM AUX_ORGANOS_PROD WHERE CD_Organo = u and CD_Division = w)) OR u is null)))
  ----
  AND (ES.CD_DIV_DESTINO = w OR w = 0)
  AND (ES.CD_PROV_DESTINO = x OR x = 0)
  AND (ES.CD_LOC_DESTINO = y OR y = 0)
  ----
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND UUU.CD_USUARIO = v
 	AND ES.Estado IS NULL

--ORDER BY OrgDestino, Num_RegistroOF;
ORDER BY DivDestino, OrganoDestino, ProvDestino, LocDestino, Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_IndicesE_fechas_MIN;


/*************************************************************************************************/

PROCEDURE usp_RPT_IndicesEDes_fechas_MIN(p IN VARCHAR2, kk IN VARCHAR2, cc IN VARCHAR2,
                                      s IN INTEGER, t IN INTEGER, u IN VARCHAR2,
                                      v IN VARCHAR2, w IN INTEGER, x IN INTEGER, y IN INTEGER,
                                      z IN VARCHAR2, cursorOut OUT t_cursor)
IS
ret_cursor t_cursor;

fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

BEGIN

  OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE
   WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				        RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
	  THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		    TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
        				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				        AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
	END) AS NumRegistro,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha,
	US.DS_Usuario,
  ----
  (SELECT DS_PROVINCIA FROM AUX_PROVINCIAS
   WHERE CD_PROVINCIA = ES.CD_PROV_ORIGEN) || ' ' ||
  NVL((SELECT ' / ' || DS_LOCALIDAD FROM AUX_LOCALIDADES
   WHERE CD_PROVINCIA = ES.CD_PROV_ORIGEN AND CD_LOCALIDAD = ES.CD_LOC_ORIGEN),' ') AS ProvLocOrigen,

  ----
	(CASE
   WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
     THEN CASE
            WHEN OG.CD_Organosup IS NULL
			        THEN DEP.DS_Departamento || '.' || OG.DS_Organo ||
                   NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                   			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
             	ELSE DEP.DS_Departamento || '.' || OG.DS_Organo ||
                    ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                             WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                              AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                   NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                   			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			    END
     ELSE
     		  CASE
            WHEN ES.CD_Org_Origen IS NOT NULL
            	THEN
                CASE WHEN OG.CD_Organosup IS NULL
                	THEN OG.DS_Organo ||
                  		 NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                       		  RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                  ELSE OG.DS_Organo || ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                                WHERE	OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                                                 AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                       NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                       			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				          END
		          ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
              			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		      END
	   END) AS OrgOrigen,

	ES.CD_Org_Destino AS CodOrganoD,
  ----
  (SELECT DS_PROVINCIA FROM AUX_PROVINCIAS
   WHERE CD_PROVINCIA = ES.CD_PROV_DESTINO) AS ProvDestino,
  (SELECT DS_LOCALIDAD FROM AUX_LOCALIDADES
   WHERE CD_PROVINCIA = ES.CD_PROV_DESTINO AND CD_LOCALIDAD = ES.CD_LOC_DESTINO) AS LocDestino,
  (SELECT DS_DIVISION FROM AUX_DIVISIONES
   WHERE CD_DIVISION = ES.CD_DIV_DESTINO) AS DivDestino,

	(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN DEP1.DS_Departamento || '.' || OG1.DS_Organo
			ELSE DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
       (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
         AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
			END
	ELSE
		CASE WHEN CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN OG1.DS_Organo
				ELSE OG1.DS_Organo || ' (' ||
         (SELECT DS_Organo FROM AUX_ORGANOS_PROD
					WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
          AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrganoDestino,

  ----
  (SELECT DS_PROVINCIA FROM AUX_PROVINCIAS
   WHERE CD_PROVINCIA = ES.CD_PROV_DESTINO) || ' ' ||
  (SELECT DS_LOCALIDAD FROM AUX_LOCALIDADES
   WHERE CD_PROVINCIA = ES.CD_PROV_DESTINO AND CD_LOCALIDAD = ES.CD_LOC_DESTINO) || ' ' ||
  (SELECT DS_DIVISION FROM AUX_DIVISIONES
   WHERE CD_DIVISION = ES.CD_DIV_DESTINO) || ' ' ||
  ----
	(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN DEP1.DS_Departamento || '.' || OG1.DS_Organo
			ELSE DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
       (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
         AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
			END
	ELSE
		CASE WHEN CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN OG1.DS_Organo
				ELSE OG1.DS_Organo || ' (' ||
         (SELECT DS_Organo FROM AUX_ORGANOS_PROD
					WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
          AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
	ES.Resumen,
	(select c.DS_Logo
   from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = z)
   as Organismo,
  Descripcion
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1,
  UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, REGISTROESTRA EST
WHERE
	EST.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Div_Origen=OG.CD_Division(+))
	AND (ES.CD_Org_Origen=OG.CD_Organo(+))
	AND ES.CD_Org_Origen=DEP.CD_Organo(+)
	AND ES.CD_Dep_Origen=DEP.CD_Departamento(+)
	AND ES.CD_Org_Destino=DEP1.CD_Organo(+)
	AND ES.CD_Dep_Destino=DEP1.CD_Departamento(+)
	AND (ES.CD_Div_Destino = OG1.CD_Division)
	AND (ES.CD_Org_Destino = OG1.CD_Organo)
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarRegistro'
	AND (trunc(EST.F_EFECTOS) >= fv and trunc(EST.F_EFECTOS) <= ff)
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	AND ( (ES.CD_Org_Destino  = u OR u is null)
		    OR

        ((ES.CD_Org_Destino IN
        	 (SELECT CD_Organo FROM AUX_ORGANOS_PROD WHERE CD_Oficina =
          	  (SELECT CD_Oficina FROM AUX_ORGANOS_PROD WHERE CD_Organo = u and CD_Division = w)) OR u is null)))

  ----
  AND (ES.CD_DIV_DESTINO = w OR w = 0)
  AND (ES.CD_PROV_DESTINO = x OR x = 0)
  AND (ES.CD_LOC_DESTINO = y OR y = 0)
  ----
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
  AND (UUU.CD_USUARIO = v OR v is null)
  AND ES.Usuario = UUU.DNI
	AND ES.Estado IS NULL
UNION
SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,

	OFI.DS_Oficina,
	ES.FechaP AS Fecha,

	US.DS_Usuario,
  ----
  (SELECT DS_PROVINCIA FROM AUX_PROVINCIAS
   WHERE CD_PROVINCIA = ES.CD_PROV_ORIGEN) || ' ' ||
  NVL((SELECT ' / ' || DS_LOCALIDAD FROM AUX_LOCALIDADES
   WHERE CD_PROVINCIA = ES.CD_PROV_ORIGEN AND CD_LOCALIDAD = ES.CD_LOC_ORIGEN),' ') AS ProvLocOrigen,
  ----
	(CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
         AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG.DS_Organo || ' (' ||
        (SELECT DS_Organo FROM AUX_ORGANOS_PROD
					WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
          AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,

	EST.CD_Oficina AS CodOrganoD,
  ----
  (SELECT DS_PROVINCIA FROM AUX_PROVINCIAS
   WHERE CD_PROVINCIA = ES.CD_PROV_DESTINO) AS ProvDestino,
  (SELECT DS_LOCALIDAD FROM AUX_LOCALIDADES
   WHERE CD_PROVINCIA = ES.CD_PROV_DESTINO AND CD_LOCALIDAD = ES.CD_LOC_DESTINO) AS LocDestino,
  (SELECT DS_DIVISION FROM AUX_DIVISIONES
   WHERE CD_DIVISION = ES.CD_DIV_DESTINO) AS DivDestino,
	(SELECT AUX_OFICINAS.DS_Oficina FROM AUX_OFICINAS
   WHERE AUX_OFICINAS.CD_Oficina = EST.CD_Oficina) AS OrganoDestino,
  ----
  (SELECT DS_PROVINCIA FROM AUX_PROVINCIAS
   WHERE CD_PROVINCIA = ES.CD_PROV_DESTINO) || ' ' ||
  (SELECT DS_LOCALIDAD FROM AUX_LOCALIDADES
   WHERE CD_PROVINCIA = ES.CD_PROV_DESTINO AND CD_LOCALIDAD = ES.CD_LOC_DESTINO) || ' ' ||
  (SELECT DS_DIVISION FROM AUX_DIVISIONES
   WHERE CD_DIVISION = ES.CD_DIV_DESTINO) || ' ' ||
  ----
	(SELECT AUX_OFICINAS.DS_Oficina FROM AUX_OFICINAS WHERE AUX_OFICINAS.CD_Oficina = EST.CD_Oficina) AS OrgDestino,
	' /' ||ES.CD_Regis_Original AS CD_Regis_Original,
	ES.Resumen,
	(select c.DS_Logo
   from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = z)
   as Organismo,
  Descripcion
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS_PROD OG,
  UsuariosV1 US,	DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, REGISTROESTRA EST
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Div_Origen=OG.CD_Division(+))
	AND (ES.CD_Org_Origen=OG.CD_Organo(+))
	AND (ES.CD_Org_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarUnidad'
	AND (TRUNC(EST.F_EFECTOS) >= fv and trunc(EST.F_EFECTOS) <= ff)
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	AND ( (ES.CD_Org_Destino  = u OR u is null)
		    OR

        ((ES.CD_Org_Destino IN
        	 (SELECT CD_Organo FROM AUX_ORGANOS_PROD WHERE CD_Oficina =
           	  (SELECT CD_Oficina FROM AUX_ORGANOS_PROD WHERE CD_Organo = u and CD_Division = w)) OR u is null)))
  ----
  AND (ES.CD_DIV_DESTINO = w OR w = 0)
  AND (ES.CD_PROV_DESTINO = x OR x = 0)
  AND (ES.CD_LOC_DESTINO = y OR y = 0)
  ----
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
  AND (UUU.CD_USUARIO = v OR v is null)
  AND ES.Usuario = UUU.DNI
 	AND ES.Estado IS NULL

ORDER BY DivDestino, OrganoDestino, ProvDestino, LocDestino, Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_IndicesEDes_fechas_MIN;



/*************************************************************************************************/
PROCEDURE usp_RPT_IndicesE_fechas_MAP
  (p IN VARCHAR2, kk IN VARCHAR2, cc IN VARCHAR2,
   s IN INTEGER, t IN INTEGER, u IN VARCHAR2,
   v IN VARCHAR2, w IN INTEGER, x IN INTEGER,
   y IN VARCHAR2, z IN VARCHAR2, -- z concatena mostrar suborganos y el tipo asunto
   cursorOut OUT t_cursor)
IS
  ret_cursor t_cursor;

  fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
  ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

-- Proceso para separar la cadena
            TYPE registro IS RECORD
            (parametro VARCHAR2(500));


            TYPE v_registro IS TABLE OF registro
                 INDEX BY BINARY_INTEGER;

            vr_registro v_registro;

            v_cadena VARCHAR2(1000);
            v_contador NUMBER(4);
            CONT NUMBER(4);
            POS  NUMBER(4);

            V_Z NUMBER(4);  -- v_z=1 Muestra Subórganos, z=0 No los muestra
            V_TA NUMBER(4); -- Tipo Asunto

          v_fechaini number := to_number(to_char(fv, 'j'));
          v_fechafin number := to_number(to_char(ff, 'j'));
          v_fecha number;
          v_date date;
          v_sql varchar2(2000);

BEGIN

-- Inicio del proceso de separación de la cadena (z)
            v_cadena := z;

            -- Inicializamos el registro
            FOR CONT IN  0..7 LOOP
                vr_registro(CONT).parametro:='';
            END LOOP;
   DBMS_OUTPUT.PUT_LINE ('paso 0');
            -- Separamos los parámetros que vienen en la nueva cadena de entrada
            v_contador:= 0;
            WHILE LENGTH(v_cadena) > 0 LOOP

                IF INSTR(v_cadena,'~') != 0 THEN
                    POS := INSTR(v_cadena,'~') ;
                    vr_registro(v_contador).parametro := SUBSTR(v_cadena,1,POS-1);

            --        dbms_output.put_line( 'POS '|| POS);
                    v_cadena := SUBSTR(v_cadena, POS+1);
                    v_contador := v_contador + 1;

                ELSE
                   vr_registro(v_contador).parametro := v_cadena;
                   v_contador := v_contador + 1;
                   v_cadena := '';
                END IF;

            END LOOP;

            -- Visualizacion del registro (todos los parámetros)

   DBMS_OUTPUT.PUT_LINE ('vr_registro(0).parametro: '||vr_registro(0).parametro);
   DBMS_OUTPUT.PUT_LINE ('vr_registro(1).parametro: '||vr_registro(1).parametro);

   V_Z:= TO_NUMBER (vr_registro(0).parametro);
   V_TA:= TO_NUMBER (vr_registro(1).parametro);

IF x=0 THEN

/* Si en el formulario NO se marca el checkbox de 'Usar Fecha Registro', se realiza la select que
 había en un principio. En caso de que se marque se modifica el where usando la fecha del registro
 en lugar de la fecha de envío*/

    --IF ff - fv < 365 THEN
    IF ff - fv < 10 THEN

        DELETE FROM TT_REGISTROESTRA2;
        COMMIT;

        begin

          for v_fecha in v_fechaini..v_fechafin
          loop
             v_sql := ' INSERT INTO TT_REGISTROESTRA2 SELECT EJERCICIO, TIPOES, NUM_REGISTRO, F_PENDIENTE, CD_CAMINO, CD_TRAMITE, CD_USUARIO_PENDIENTE, F_EJECUCION, CD_USUARIO_EJECUCION, F_EFECTOS, F_VENCIMIENTO, COMENTARIO, INFOEJEC, CRITICO, CD_OFICINA, CD_ORGANO, CD_DEPARTAMENTO, FIRMANTE1, FIRMANTE2, CD_MOTIVOR, CD_DIVISION, DEVUELTO FROM REGISTROESTRA WHERE FECHA_SH = :v_date';
             v_date := to_date(v_fecha,'j');
             execute immediate v_sql  using v_date;
             commit;
           end loop;
        end;

        /*
        INSERT INTO TT_REGISTROESTRA2
        (EJERCICIO, TIPOES, NUM_REGISTRO, F_PENDIENTE, CD_CAMINO, CD_TRAMITE, CD_USUARIO_PENDIENTE, F_EJECUCION, CD_USUARIO_EJECUCION, F_EFECTOS, F_VENCIMIENTO, COMENTARIO, INFOEJEC, CRITICO, CD_OFICINA, CD_ORGANO, CD_DEPARTAMENTO, FIRMANTE1, FIRMANTE2, CD_MOTIVOR, CD_DIVISION, DEVUELTO)
        SELECT EJERCICIO, TIPOES, NUM_REGISTRO, F_PENDIENTE, CD_CAMINO, CD_TRAMITE, CD_USUARIO_PENDIENTE, F_EJECUCION, CD_USUARIO_EJECUCION, F_EFECTOS, F_VENCIMIENTO, COMENTARIO, INFOEJEC, CRITICO, CD_OFICINA, CD_ORGANO, CD_DEPARTAMENTO, FIRMANTE1, FIRMANTE2, CD_MOTIVOR, CD_DIVISION, DEVUELTO
        FROM REGISTROESTRA
        WHERE FECHA_SH BETWEEN fv AND ff;
        --WHERE F_EFECTOS BETWEEN fv - 1 AND ff + 1;
        COMMIT;
        */

        OPEN ret_cursor FOR

        SELECT DISTINCT
            fv AS fechai,ff AS fechaf,
            ES.Num_RegistroOF,
            (CASE
           WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
                                RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                        AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
              THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
                    TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
                                RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                                AND RG.Num_Registro = ES.Num_RegistroOF))
                ELSE TO_CHAR(ES.Num_RegistroOF)
            END) AS NumRegistro,
            --ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
            --ES.FECHA_REGIS_ORIGINAL AS Fecharem,
            OFI.DS_Oficina,
            ES.FechaP AS Fecha,
            --DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
            US.DS_Usuario,
            (CASE
           WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
             THEN CASE
                    WHEN OG.CD_Organosup IS NULL
                            THEN DEP.DS_Departamento || '.' || OG.DS_Organo ||
                           NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                       RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                         ELSE DEP.DS_Departamento || '.' || OG.DS_Organo ||
                            ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                     WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                                      AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                           NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                       RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                        END
             ELSE
                       CASE
                    WHEN ES.CD_Org_Origen IS NOT NULL
                        THEN
                        CASE WHEN OG.CD_Organosup IS NULL
                            THEN OG.DS_Organo ||
                                   NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                         RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                          ELSE OG.DS_Organo || ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                                        WHERE    OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                                                         AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                               NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                           RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                                  END
                          ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                  RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                      END
               END) AS OrgOrigen,
          /*
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
            (SELECT MAX(LOCALIDAD) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                END AS LOCALIDAD,
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
            (SELECT MAX(PAIS) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                END AS PAIS,
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
                (SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
              (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
            END AS PROVINCIA,
          */
            ES.CD_Org_Destino AS CodOrganoD,
          ES.Cd_Div_Destino,

            (CASE WHEN ES.CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL AND
                     (SELECT DISTINCT T.CD_Tramite
                      FROM RegistroESTra T
                      WHERE ES.Ejercicio = T.Ejercicio
                            AND ES.TipoES = T.TipoES
                            AND ES.Num_Registro = T.Num_Registro
                            AND T.CD_tramite = 'RevisarORG') IS NULL
                THEN CASE WHEN OG1.CD_Organosup IS NULL
                    THEN DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo
                    ELSE DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
               (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                        WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                 AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
                    END
            ELSE
                CASE WHEN ES.CD_Org_Destino IS NOT NULL
                    THEN CASE WHEN OG1.CD_Organosup IS NULL
                           --- Incluido dept destino
                   THEN DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo
                           ELSE DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
                   ---
                      (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                      AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')'
                           END
                    ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                  END
            END) AS OrgDestino,


             (select rel1.direccion || ' ' || rel1.cp || ' ' || loc1.ds_localidad
            from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
            where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
             and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
             and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as DireccionOrgDestino,

          (select loc1.cd_provincia
            from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
            where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
             and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
             and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as CodigoProvincia,

            --' /' || ES.CD_Regis_Original AS CD_Regis_Original,
            --DECODE(ES.Tipo_Regis_Original,'E',' / Entrada','S',' / Salida','') AS Tipo_Regis_original,
            --ES.Num_Regis_Original,
            --ES.Fecha_Regis_Original,
            --ES.Tipo_Transporte,
            --TR.DS_Transporte,
            --ES.Num_Transporte,
            NVL(CM.CONTENIDO,'') || ' ' || ES.Resumen ||'. '|| ES.Obs as Resumen, -- Incidencia 10529. Se junta el resumen con las observaciones
            --ES.ExpOferta,
            --OFI.Direccion,
            --OFI.CP,
            --OFI.Poblacion,
            OFI.Direccion || ' ' ||
            OFI.CP || ' ' ||
          (select loc2.DS_Localidad from AUX_LOCALIDADES loc2
           where loc2.CD_Provincia = OFI.CD_PROVINCIA and loc2.CD_Localidad = OFI.Cd_Localidad) as DireccionOficina,
            --OFI.Telefono,
            --OFI.FAX,
            --ES.ExpOferta,

          -- Dani 09/05/2007
            --(select c.DS_Logo
          -- from Dat_Config c inner join USUARIOS uu on
          --  uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = v)
          -- as Organismo
            (select c.DS_Logo from Dat_Config c where CD_Organismo = 5) as Organismo,
            CASE WHEN v_ta IS NOT NULL
               THEN CM.DS_COMENTARIO
               ELSE ''
            END AS TipoAsunto


        FROM
            AUX_OFICINAS OFI, AUX_OFICINAS OFI2, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1, AUX_TRANSPORTES TR,
          UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
            AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, TT_REGISTROESTRA2 EST, AUX_COMENTARIOS CM

        WHERE
            EST.CD_Oficina = OFI.CD_Oficina
            AND (ES.CD_Div_Origen=OG.CD_Division(+))
            AND (ES.CD_Org_Origen=OG.CD_Organo(+))
            AND ES.CD_Org_Origen=DEP.CD_Organo(+)
            AND ES.CD_Dep_Origen=DEP.CD_Departamento(+)
            AND ES.CD_Org_Destino=DEP1.CD_Organo(+)
            AND ES.CD_Dep_Destino=DEP1.CD_Departamento(+)
            AND (ES.CD_Div_Destino = OG1.CD_Division)
            AND (ES.CD_Org_Destino = OG1.CD_Organo)
            AND ES.Tipo_Transporte=TR.CD_Transporte(+)
            AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
            AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
            AND ES.Usuario = US.DNI
            AND OFI.CD_Oficina = p
            AND ES.TipoES = 'E'
            AND ES.Ejercicio = EST.Ejercicio
            AND ES.TipoES = EST.TipoES
            AND ES.Num_Registro = EST.Num_Registro
            AND EST.CD_Tramite = 'EnviarRegistro'
          --AND (TRUNC(EST.F_EFECTOS) >= fv and trunc(EST.F_EFECTOS) <= ff) --Fecha Envío
          --  AND (fv IS NOT NULL OR ff IS NOT NULL)
            AND (ES.Num_RegistroOF >= s OR s = 0)
            AND (ES.Num_RegistroOF <= t OR t = 0)
            AND ( (ES.CD_Org_Destino  = u OR u is null)
                    OR
                ((ES.CD_Org_Destino IN
                     (SELECT CD_Organo FROM AUX_ORGANOS_PROD WHERE CD_Oficina =
                         (SELECT CD_Oficina FROM AUX_ORGANOS_PROD WHERE CD_Organo = u and CD_Division = w)) OR u is null)))
          ----
          AND (ES.CD_DIV_DESTINO = w OR w = 0)
          ----
            AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO

          -- Dani 09/05/2007
            --AND UUU.CD_USUARIO = v
            AND (UUU.CD_USUARIO = v OR v is null)

            AND ES.Estado IS NULL
          AND ES.Usuario = UUU.DNI
          AND (DEP1.CD_Departamento=y OR y is NULL)
          AND OG1.CD_Oficina = OFI2.CD_Oficina (+)
          AND ((v_ta IS NOT NULL AND TO_CHAR(ES.CD_TIPOASUNTO_COM) = v_ta) OR
                       (v_ta is null))
          --AND ((V_Z=0 and EST.cd_departamento is null) OR (V_Z=1 and EST.cd_departamento is not null))
          AND ((V_Z=0 and ES.CD_DEP_DESTINO is null) OR (V_Z=1 and ES.CD_DEP_DESTINO is not null))

        UNION
        SELECT DISTINCT
            fv AS fechai,ff AS fechaf,
            ES.Num_RegistroOF,
            (CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
                        RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                        AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
                THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
                     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
                        RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                        AND RG.Num_Registro = ES.Num_RegistroOF))
                ELSE TO_CHAR(ES.Num_RegistroOF)
                END) AS NumRegistro,
            --ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
            --ES.FECHA_REGIS_ORIGINAL AS Fecharem,
            OFI.DS_Oficina,
            ES.FechaP AS Fecha,
            --DECODE(ES.TipoES, 'E', 'Entrada', 'S', 'Salida', '') AS TipoES,
            US.DS_Usuario,
            (CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
                THEN CASE WHEN OG.CD_Organosup IS NULL
                    THEN    DEP.DS_Departamento || '.' || OG.DS_Organo ||
                        NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                            RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                    ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
              (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                        WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                 AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                        NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                            RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                    END
            ELSE
                CASE WHEN ES.CD_Org_Origen IS NOT NULL
                    THEN CASE WHEN OG.CD_Organosup IS NULL
                        THEN    OG.DS_Organo ||
                            NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                        ELSE  OG.DS_Organo || ' (' ||
                (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                            WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                  AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                            NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                        END
                    ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                END
            END) AS OrgOrigen,
          /*
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
            (SELECT MAX(LOCALIDAD) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                END AS LOCALIDAD,
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
            (SELECT MAX(PAIS) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                END AS PAIS,
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
                (SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
              (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
            END AS PROVINCIA,
          */
            EST.CD_Oficina AS CodOrganoD,
          ES.Cd_Div_Destino,
            --(SELECT AUX_OFICINAS.DS_Oficina FROM AUX_OFICINAS WHERE AUX_OFICINAS.CD_Oficina = EST.CD_Oficina) AS OrgDestino,
        (CASE WHEN ES.CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL AND
                     (SELECT DISTINCT T.CD_Tramite
                      FROM RegistroESTra T
                      WHERE ES.Ejercicio = T.Ejercicio
                            AND ES.TipoES = T.TipoES
                            AND ES.Num_Registro = T.Num_Registro
                            AND T.CD_tramite = 'RevisarORG') IS NULL
                THEN CASE WHEN OG1.CD_Organosup IS NULL
                    THEN DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' - (' || OFI2.DS_Oficina || ')'
                    ELSE DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
               (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                        WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                 AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' || ' - (' || OFI2.DS_Oficina || ')'
                    END
            ELSE
                CASE WHEN ES.CD_Org_Destino IS NOT NULL
                    THEN CASE WHEN OG1.CD_Organosup IS NULL
                           THEN ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' - (' || OFI2.DS_Oficina || ')'
                           ELSE ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
                      (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                      AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' || ' - (' || OFI2.DS_Oficina || ')'
                           END
                    ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                  END
            END) AS OrgDestino,


        --  (select max(ofi2.Direccion) || ' ' || max(ofi2.CP) || ' ' || max(loc3.DS_Localidad)
        --   from AUX_OFICINAS ofi2, AUX_LOCALIDADES loc3
        --   where loc3.CD_Provincia = ofi2.CD_PROVINCIA and loc3.CD_Localidad = ofi2.CD_Localidad
        --    and ofi2.Cd_Oficina = EST.CD_OFICINA) as DireccionOrgDestino,

             (select rel1.direccion || ' ' || rel1.cp || ' ' || loc1.ds_localidad
            from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
            where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
             and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
             and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as DireccionOrgDestino,

             (select loc1.cd_provincia
            from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
            where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
             and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
             and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as CodigoProvincia,

            --' /' ||ES.CD_Regis_Original AS CD_Regis_Original,
            --DECODE(ES.Tipo_Regis_Original,'E',' / Entrada','S',' / Salida','') AS Tipo_Regis_original,
            --ES.Num_Regis_Original,
            --ES.Fecha_Regis_Original,
            --ES.Tipo_Transporte,
            --TR.DS_Transporte,
            --ES.Num_Transporte,
            NVL(CM.CONTENIDO,'') || ' ' || ES.Resumen ||'. '|| ES.Obs as Resumen, -- Incidencia 10529. Se junta el resumen con las observaciones
            --ES.ExpOferta,
            --OFI.Direccion,
            --OFI.CP,
            --OFI.Poblacion,
            OFI.Direccion || ' ' ||
            OFI.CP || ' ' ||
          (select loc4.DS_Localidad from AUX_LOCALIDADES loc4
           where loc4.CD_Provincia = OFI.CD_PROVINCIA and loc4.CD_Localidad = OFI.Cd_Localidad) as DireccionOficina,
            --OFI.Telefono,
            --OFI.FAX,
            --ES.ExpOferta,

          -- Dani 09/05/2007
            --(select c.DS_Logo
          -- from Dat_Config c inner join USUARIOS uu on
          --  uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = v)
          -- as Organismo
            (select c.DS_Logo from Dat_Config c where CD_Organismo = 5) as Organismo,
            CASE WHEN v_ta IS NOT NULL
               THEN CM.DS_COMENTARIO
               ELSE ''
            END AS TipoAsunto


        FROM
            --AUX_OFICINAS OFI, AUX_OFICINAS OFI2, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_TRANSPORTES TR, UsuariosV1 US,
            --DAT_CONFIG CC, USUARIOS uuu,
            --AUX_DEPARTAMENTOS DEP, REGISTROESTRA EST
            AUX_OFICINAS OFI, AUX_OFICINAS OFI2, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1, AUX_TRANSPORTES TR,
          UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
            AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, TT_REGISTROESTRA2 EST, AUX_COMENTARIOS CM

        WHERE
            ES.CD_Oficina = OFI.CD_Oficina
            AND (ES.CD_Div_Origen=OG.CD_Division(+))
            AND (ES.CD_Org_Origen=OG.CD_Organo(+))
            AND (ES.CD_Org_Origen=DEP.CD_Organo(+))
            AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
            AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
            AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
            AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
            AND ES.Usuario = US.DNI
            AND OFI.CD_Oficina = p
            AND ES.TipoES = 'E'
            AND ES.Ejercicio = EST.Ejercicio
            AND ES.TipoES = EST.TipoES
            AND ES.Num_Registro = EST.Num_Registro
            AND EST.CD_Tramite = 'EnviarUnidad'
            --AND (TRUNC(EST.F_EFECTOS) >= fv and trunc(EST.F_EFECTOS) <= ff) --Fecha Envío
            --AND (fv IS NOT NULL OR ff IS NOT NULL)
            AND (ES.Num_RegistroOF >= s OR s = 0)
            AND (ES.Num_RegistroOF <= t OR t = 0)
            AND ( (ES.CD_Org_Destino  = u OR u is null)
                   OR ((ES.CD_Org_Destino IN
                    (SELECT CD_Organo FROM AUX_ORGANOS_PROD WHERE CD_Oficina =
                       (SELECT CD_Oficina FROM AUX_ORGANOS_PROD WHERE CD_Organo = u and CD_Division = w)) OR u is null)))
          ----
          AND (ES.CD_DIV_DESTINO = w OR w = 0)
          ----
            AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO

          -- Dani 09/05/2007
            --AND UUU.CD_USUARIO = v
            AND (UUU.CD_USUARIO = v OR v is null)

             AND ES.Estado IS NULL
          AND ES.Usuario = UUU.DNI
          AND OG1.CD_Oficina = OFI2.CD_Oficina (+)
          AND ES.CD_Org_Destino=DEP1.CD_Organo(+)
            AND ES.CD_Dep_Destino=DEP1.CD_Departamento(+)
            AND (ES.CD_Div_Destino = OG1.CD_Division)
            AND (ES.CD_Org_Destino = OG1.CD_Organo)
          AND (DEP1.CD_Departamento=y OR y is NULL)
          AND ((v_ta IS NOT NULL AND TO_CHAR(ES.CD_TIPOASUNTO_COM) = v_ta) OR
                       (v_ta is null))
          --AND ((V_Z=0 and EST.cd_departamento is null) OR (V_Z=1 and EST.cd_departamento is not null))
          AND ((V_Z=0 and ES.CD_DEP_DESTINO is null) OR (V_Z=1 and ES.CD_DEP_DESTINO is not null))

        ORDER BY  OrgDestino, Num_RegistroOF; --NumRegistro;
        --ORDER BY  OrgDestino, Num_RegistroOF;
        --ORDER BY  CodOrganoD, Num_RegistroOF;

    ELSE -- NO SE USA LA TABLA TEMPORAL


          OPEN ret_cursor FOR

        SELECT DISTINCT
            fv AS fechai,ff AS fechaf,
            ES.Num_RegistroOF,
            (CASE
           WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
                                RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                        AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
              THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
                    TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
                                RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                                AND RG.Num_Registro = ES.Num_RegistroOF))
                ELSE TO_CHAR(ES.Num_RegistroOF)
            END) AS NumRegistro,
            --ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
            --ES.FECHA_REGIS_ORIGINAL AS Fecharem,
            OFI.DS_Oficina,
            ES.FechaP AS Fecha,
            --DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
            US.DS_Usuario,
            (CASE
           WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
             THEN CASE
                    WHEN OG.CD_Organosup IS NULL
                            THEN DEP.DS_Departamento || '.' || OG.DS_Organo ||
                           NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                       RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                         ELSE DEP.DS_Departamento || '.' || OG.DS_Organo ||
                            ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                     WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                                      AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                           NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                       RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                        END
             ELSE
                       CASE
                    WHEN ES.CD_Org_Origen IS NOT NULL
                        THEN
                        CASE WHEN OG.CD_Organosup IS NULL
                            THEN OG.DS_Organo ||
                                   NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                         RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                          ELSE OG.DS_Organo || ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                                        WHERE    OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                                                         AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                               NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                           RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                                  END
                          ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                  RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                      END
               END) AS OrgOrigen,
          /*
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
            (SELECT MAX(LOCALIDAD) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                END AS LOCALIDAD,
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
            (SELECT MAX(PAIS) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                END AS PAIS,
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
                (SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
              (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
            END AS PROVINCIA,
          */
            ES.CD_Org_Destino AS CodOrganoD,
          ES.Cd_Div_Destino,

            (CASE WHEN ES.CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL AND
                     (SELECT DISTINCT T.CD_Tramite
                      FROM RegistroESTra T
                      WHERE ES.Ejercicio = T.Ejercicio
                            AND ES.TipoES = T.TipoES
                            AND ES.Num_Registro = T.Num_Registro
                            AND T.CD_tramite = 'RevisarORG') IS NULL
                THEN CASE WHEN OG1.CD_Organosup IS NULL
                    THEN DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo
                    ELSE DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
               (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                        WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                 AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
                    END
            ELSE
                CASE WHEN ES.CD_Org_Destino IS NOT NULL
                    THEN CASE WHEN OG1.CD_Organosup IS NULL
                           --- Incluido dept destino
                   THEN DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo
                           ELSE DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
                   ---
                      (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                      AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')'
                           END
                    ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                  END
            END) AS OrgDestino,


             (select rel1.direccion || ' ' || rel1.cp || ' ' || loc1.ds_localidad
            from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
            where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
             and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
             and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as DireccionOrgDestino,

          (select loc1.cd_provincia
            from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
            where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
             and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
             and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as CodigoProvincia,

            --' /' || ES.CD_Regis_Original AS CD_Regis_Original,
            --DECODE(ES.Tipo_Regis_Original,'E',' / Entrada','S',' / Salida','') AS Tipo_Regis_original,
            --ES.Num_Regis_Original,
            --ES.Fecha_Regis_Original,
            --ES.Tipo_Transporte,
            --TR.DS_Transporte,
            --ES.Num_Transporte,
            NVL(CM.CONTENIDO,'') || ' ' || ES.Resumen ||'. '|| ES.Obs as Resumen, -- Incidencia 10529. Se junta el resumen con las observaciones
            --ES.ExpOferta,
            --OFI.Direccion,
            --OFI.CP,
            --OFI.Poblacion,
            OFI.Direccion || ' ' ||
            OFI.CP || ' ' ||
          (select loc2.DS_Localidad from AUX_LOCALIDADES loc2
           where loc2.CD_Provincia = OFI.CD_PROVINCIA and loc2.CD_Localidad = OFI.Cd_Localidad) as DireccionOficina,
            --OFI.Telefono,
            --OFI.FAX,
            --ES.ExpOferta,

          -- Dani 09/05/2007
            --(select c.DS_Logo
          -- from Dat_Config c inner join USUARIOS uu on
          --  uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = v)
          -- as Organismo
            (select c.DS_Logo from Dat_Config c where CD_Organismo = 5) as Organismo,
            CASE WHEN v_ta IS NOT NULL
               THEN CM.DS_COMENTARIO
               ELSE ''
            END AS TipoAsunto


        FROM
            AUX_OFICINAS OFI, AUX_OFICINAS OFI2, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1, AUX_TRANSPORTES TR,
          UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
            AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, REGISTROESTRA EST, AUX_COMENTARIOS CM

        WHERE
            EST.CD_Oficina = OFI.CD_Oficina
            AND (ES.CD_Div_Origen=OG.CD_Division(+))
            AND (ES.CD_Org_Origen=OG.CD_Organo(+))
            AND ES.CD_Org_Origen=DEP.CD_Organo(+)
            AND ES.CD_Dep_Origen=DEP.CD_Departamento(+)
            AND ES.CD_Org_Destino=DEP1.CD_Organo(+)
            AND ES.CD_Dep_Destino=DEP1.CD_Departamento(+)
            AND (ES.CD_Div_Destino = OG1.CD_Division)
            AND (ES.CD_Org_Destino = OG1.CD_Organo)
            AND ES.Tipo_Transporte=TR.CD_Transporte(+)
            AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
            AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
            AND ES.Usuario = US.DNI
            AND OFI.CD_Oficina = p
            AND ES.TipoES = 'E'
            AND ES.Ejercicio = EST.Ejercicio
            AND ES.TipoES = EST.TipoES
            AND ES.Num_Registro = EST.Num_Registro
            AND EST.CD_Tramite = 'EnviarRegistro'
            --AND (TRUNC(EST.F_EFECTOS) >= fv and trunc(EST.F_EFECTOS) <= ff) --Fecha Envío
            AND (EST.FECHA_SH >= fv and EST.FECHA_SH <= ff) --Fecha Envío
            AND (fv IS NOT NULL OR ff IS NOT NULL)
            AND (ES.Num_RegistroOF >= s OR s = 0)
            AND (ES.Num_RegistroOF <= t OR t = 0)
            AND ( (ES.CD_Org_Destino  = u OR u is null)
                    OR
                ((ES.CD_Org_Destino IN
                     (SELECT CD_Organo FROM AUX_ORGANOS_PROD WHERE CD_Oficina =
                         (SELECT CD_Oficina FROM AUX_ORGANOS_PROD WHERE CD_Organo = u and CD_Division = w)) OR u is null)))
          ----
          AND (ES.CD_DIV_DESTINO = w OR w = 0)
          ----
            AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO

          -- Dani 09/05/2007
            --AND UUU.CD_USUARIO = v
            AND (UUU.CD_USUARIO = v OR v is null)

            AND ES.Estado IS NULL
          AND ES.Usuario = UUU.DNI
          AND (DEP1.CD_Departamento=y OR y is NULL)
          AND OG1.CD_Oficina = OFI2.CD_Oficina (+)
          AND ((v_ta IS NOT NULL AND TO_CHAR(ES.CD_TIPOASUNTO_COM) = v_ta) OR
                       (v_ta is null))
          --AND ((V_Z=0 and EST.cd_departamento is null) OR (V_Z=1 and EST.cd_departamento is not null))
          AND ((V_Z=0 and ES.CD_DEP_DESTINO is null) OR (V_Z=1 and ES.CD_DEP_DESTINO is not null))

        UNION
        SELECT DISTINCT
            fv AS fechai,ff AS fechaf,
            ES.Num_RegistroOF,
            (CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
                        RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                        AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
                THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
                     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
                        RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                        AND RG.Num_Registro = ES.Num_RegistroOF))
                ELSE TO_CHAR(ES.Num_RegistroOF)
                END) AS NumRegistro,
            --ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
            --ES.FECHA_REGIS_ORIGINAL AS Fecharem,
            OFI.DS_Oficina,
            ES.FechaP AS Fecha,
            --DECODE(ES.TipoES, 'E', 'Entrada', 'S', 'Salida', '') AS TipoES,
            US.DS_Usuario,
            (CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
                THEN CASE WHEN OG.CD_Organosup IS NULL
                    THEN    DEP.DS_Departamento || '.' || OG.DS_Organo ||
                        NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                            RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                    ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
              (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                        WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                 AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                        NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                            RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                    END
            ELSE
                CASE WHEN ES.CD_Org_Origen IS NOT NULL
                    THEN CASE WHEN OG.CD_Organosup IS NULL
                        THEN    OG.DS_Organo ||
                            NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                        ELSE  OG.DS_Organo || ' (' ||
                (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                            WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                  AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                            NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                        END
                    ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                END
            END) AS OrgOrigen,
          /*
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
            (SELECT MAX(LOCALIDAD) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                END AS LOCALIDAD,
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
            (SELECT MAX(PAIS) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                END AS PAIS,
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
                (SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
              (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
            END AS PROVINCIA,
          */
            EST.CD_Oficina AS CodOrganoD,
          ES.Cd_Div_Destino,
            --(SELECT AUX_OFICINAS.DS_Oficina FROM AUX_OFICINAS WHERE AUX_OFICINAS.CD_Oficina = EST.CD_Oficina) AS OrgDestino,
        (CASE WHEN ES.CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL AND
                     (SELECT DISTINCT T.CD_Tramite
                      FROM RegistroESTra T
                      WHERE ES.Ejercicio = T.Ejercicio
                            AND ES.TipoES = T.TipoES
                            AND ES.Num_Registro = T.Num_Registro
                            AND T.CD_tramite = 'RevisarORG') IS NULL
                THEN CASE WHEN OG1.CD_Organosup IS NULL
                    THEN DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' - (' || OFI2.DS_Oficina || ')'
                    ELSE DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
               (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                        WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                 AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' || ' - (' || OFI2.DS_Oficina || ')'
                    END
            ELSE
                CASE WHEN ES.CD_Org_Destino IS NOT NULL
                    THEN CASE WHEN OG1.CD_Organosup IS NULL
                           THEN ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' - (' || OFI2.DS_Oficina || ')'
                           ELSE ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
                      (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                      AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' || ' - (' || OFI2.DS_Oficina || ')'
                           END
                    ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                  END
            END) AS OrgDestino,


        --  (select max(ofi2.Direccion) || ' ' || max(ofi2.CP) || ' ' || max(loc3.DS_Localidad)
        --   from AUX_OFICINAS ofi2, AUX_LOCALIDADES loc3
        --   where loc3.CD_Provincia = ofi2.CD_PROVINCIA and loc3.CD_Localidad = ofi2.CD_Localidad
        --    and ofi2.Cd_Oficina = EST.CD_OFICINA) as DireccionOrgDestino,

             (select rel1.direccion || ' ' || rel1.cp || ' ' || loc1.ds_localidad
            from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
            where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
             and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
             and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as DireccionOrgDestino,

             (select loc1.cd_provincia
            from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
            where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
             and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
             and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as CodigoProvincia,

            --' /' ||ES.CD_Regis_Original AS CD_Regis_Original,
            --DECODE(ES.Tipo_Regis_Original,'E',' / Entrada','S',' / Salida','') AS Tipo_Regis_original,
            --ES.Num_Regis_Original,
            --ES.Fecha_Regis_Original,
            --ES.Tipo_Transporte,
            --TR.DS_Transporte,
            --ES.Num_Transporte,
            NVL(CM.CONTENIDO,'') || ' ' || ES.Resumen ||'. '|| ES.Obs as Resumen, -- Incidencia 10529. Se junta el resumen con las observaciones
            --ES.ExpOferta,
            --OFI.Direccion,
            --OFI.CP,
            --OFI.Poblacion,
            OFI.Direccion || ' ' ||
            OFI.CP || ' ' ||
          (select loc4.DS_Localidad from AUX_LOCALIDADES loc4
           where loc4.CD_Provincia = OFI.CD_PROVINCIA and loc4.CD_Localidad = OFI.Cd_Localidad) as DireccionOficina,
            --OFI.Telefono,
            --OFI.FAX,
            --ES.ExpOferta,

          -- Dani 09/05/2007
            --(select c.DS_Logo
          -- from Dat_Config c inner join USUARIOS uu on
          --  uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = v)
          -- as Organismo
            (select c.DS_Logo from Dat_Config c where CD_Organismo = 5) as Organismo,
            CASE WHEN v_ta IS NOT NULL
               THEN CM.DS_COMENTARIO
               ELSE ''
            END AS TipoAsunto


        FROM
            --AUX_OFICINAS OFI, AUX_OFICINAS OFI2, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_TRANSPORTES TR, UsuariosV1 US,
            --DAT_CONFIG CC, USUARIOS uuu,
            --AUX_DEPARTAMENTOS DEP, REGISTROESTRA EST
            AUX_OFICINAS OFI, AUX_OFICINAS OFI2, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1, AUX_TRANSPORTES TR,
          UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
            AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, REGISTROESTRA EST, AUX_COMENTARIOS CM

        WHERE
            ES.CD_Oficina = OFI.CD_Oficina
            AND (ES.CD_Div_Origen=OG.CD_Division(+))
            AND (ES.CD_Org_Origen=OG.CD_Organo(+))
            AND (ES.CD_Org_Origen=DEP.CD_Organo(+))
            AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
            AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
            AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
            AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
            AND ES.Usuario = US.DNI
            AND OFI.CD_Oficina = p
            AND ES.TipoES = 'E'
            AND ES.Ejercicio = EST.Ejercicio
            AND ES.TipoES = EST.TipoES
            AND ES.Num_Registro = EST.Num_Registro
            AND EST.CD_Tramite = 'EnviarUnidad'
            --AND (TRUNC(EST.F_EFECTOS) >= fv and trunc(EST.F_EFECTOS) <= ff) --Fecha Envío
            AND (EST.FECHA_SH >= fv and EST.FECHA_SH <= ff) --Fecha Envío
            --AND (fv IS NOT NULL OR ff IS NOT NULL)
            AND (ES.Num_RegistroOF >= s OR s = 0)
            AND (ES.Num_RegistroOF <= t OR t = 0)
            AND ( (ES.CD_Org_Destino  = u OR u is null)
                   OR ((ES.CD_Org_Destino IN
                    (SELECT CD_Organo FROM AUX_ORGANOS_PROD WHERE CD_Oficina =
                       (SELECT CD_Oficina FROM AUX_ORGANOS_PROD WHERE CD_Organo = u and CD_Division = w)) OR u is null)))
          ----
          AND (ES.CD_DIV_DESTINO = w OR w = 0)
          ----
            AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO

          -- Dani 09/05/2007
            --AND UUU.CD_USUARIO = v
            AND (UUU.CD_USUARIO = v OR v is null)

             AND ES.Estado IS NULL
          AND ES.Usuario = UUU.DNI
          AND OG1.CD_Oficina = OFI2.CD_Oficina (+)
          AND ES.CD_Org_Destino=DEP1.CD_Organo(+)
            AND ES.CD_Dep_Destino=DEP1.CD_Departamento(+)
            AND (ES.CD_Div_Destino = OG1.CD_Division)
            AND (ES.CD_Org_Destino = OG1.CD_Organo)
          AND (DEP1.CD_Departamento=y OR y is NULL)
          AND ((v_ta IS NOT NULL AND TO_CHAR(ES.CD_TIPOASUNTO_COM) = v_ta) OR
                       (v_ta is null))
          --AND ((V_Z=0 and EST.cd_departamento is null) OR (V_Z=1 and EST.cd_departamento is not null))
          AND ((V_Z=0 and ES.CD_DEP_DESTINO is null) OR (V_Z=1 and ES.CD_DEP_DESTINO is not null))

        ORDER BY  OrgDestino, Num_RegistroOF; --NumRegistro;
        --ORDER BY  OrgDestino, Num_RegistroOF;
        --ORDER BY  CodOrganoD, Num_RegistroOF;
END IF; -- USO O NO DE TEMPORAL
  cursorOut:=ret_cursor;

ELSE -- Se ha elegido la opción de Usar Fecha Registro ********************************************

    --IF ff - fv < 365 THEN
    IF ff - fv < 10 THEN


        DELETE FROM TT_REGISTROES2;
        COMMIT;

        begin

          for v_fecha in v_fechaini..v_fechafin
          loop
             v_sql := ' INSERT INTO TT_REGISTROES2 (EJERCICIO, TIPOES, NUM_REGISTRO, FECHA, FECHAP, CD_OFICINA, NUM_REGISTROOF, USUARIO, CD_DIVISION_DESTINO, CD_ORGANO_DESTINO, CD_DIVISION_ORIGEN, CD_ORGANO_ORIGEN, CD_DEP_ORIGEN, EJERCICIO_REGIS_ORIGINAL, CD_REGIS_ORIGINAL, TIPO_REGIS_ORIGINAL, NUM_REGIS_ORIGINAL, FECHA_REGIS_ORIGINAL, TIPO_TRANSPORTE, NUM_TRANSPORTE, RESUMEN, DESCRIPTORES, UNIDAD_REGISTRAL, CD_DEP_DESTINO, PERSONA, FECHAU, CD_EJERCICIOEXP, CD_EXPEDIENTE, CD_TIPO, OFERTA, EXPOFERTA, ESTADO, ASUNTO, USUARIOU, OBSERVACIONES, NUM_SOBRES, NACEXT, EMBAJADA, CD_TIPODOCUMENTO, FECHA_PRESEN, CD_LOC_ORIGEN, CD_PROV_ORIGEN, CD_DIV_ORIGEN, CD_ORG_ORIGEN, CD_LOC_DESTINO, CD_PROV_DESTINO, CD_DIV_DESTINO, CD_ORG_DESTINO, NUM_DOCUMENTOS, NUM_COMPULSAS, NUM_REC, NUM_RECEPCION, RESUMEN_INTER, CD_ESTADO, DESCRIPCION, OBS, CD_TIPOASUNTO_COM)';
             v_sql := v_sql || ' SELECT EJERCICIO, TIPOES, NUM_REGISTRO, FECHA, FECHAP, CD_OFICINA, NUM_REGISTROOF, USUARIO, CD_DIVISION_DESTINO, CD_ORGANO_DESTINO, CD_DIVISION_ORIGEN, CD_ORGANO_ORIGEN, CD_DEP_ORIGEN, EJERCICIO_REGIS_ORIGINAL, CD_REGIS_ORIGINAL, TIPO_REGIS_ORIGINAL, NUM_REGIS_ORIGINAL, FECHA_REGIS_ORIGINAL, TIPO_TRANSPORTE, NUM_TRANSPORTE, RESUMEN, DESCRIPTORES, UNIDAD_REGISTRAL, CD_DEP_DESTINO, PERSONA, FECHAU, CD_EJERCICIOEXP, CD_EXPEDIENTE, CD_TIPO, OFERTA, EXPOFERTA, ESTADO, ASUNTO, USUARIOU, OBSERVACIONES, NUM_SOBRES, NACEXT, EMBAJADA, CD_TIPODOCUMENTO, FECHA_PRESEN, CD_LOC_ORIGEN, CD_PROV_ORIGEN, CD_DIV_ORIGEN, CD_ORG_ORIGEN, CD_LOC_DESTINO, CD_PROV_DESTINO, CD_DIV_DESTINO, CD_ORG_DESTINO, NUM_DOCUMENTOS, NUM_COMPULSAS, NUM_REC, NUM_RECEPCION, RESUMEN_INTER, CD_ESTADO, DESCRIPCION, OBS, CD_TIPOASUNTO_COM FROM REGISTROES WHERE FECHA = :v_date';
             v_date := to_date(v_fecha,'j');
             execute immediate v_sql using v_date;
             commit;
           end loop;
        end;


        /*
        INSERT INTO TT_REGISTROES2
        --(EJERCICIO, TIPOES, NUM_REGISTRO, FECHA, FECHAP, CD_OFICINA, NUM_REGISTROOF, USUARIO, CD_DIVISION_DESTINO, CD_ORGANO_DESTINO, CD_DIVISION_ORIGEN, CD_ORGANO_ORIGEN, CD_DEP_ORIGEN, EJERCICIO_REGIS_ORIGINAL, CD_REGIS_ORIGINAL, TIPO_REGIS_ORIGINAL, NUM_REGIS_ORIGINAL, FECHA_REGIS_ORIGINAL, TIPO_TRANSPORTE, NUM_TRANSPORTE, RESUMEN, DESCRIPTORES, UNIDAD_REGISTRAL, CD_DEP_DESTINO, PERSONA, FECHAU, CD_EJERCICIOEXP, CD_EXPEDIENTE, CD_TIPO, OFERTA, EXPOFERTA, ESTADO, ASUNTO, USUARIOU, OBSERVACIONES, NUM_SOBRES, NACEXT, EMBAJADA, CD_TIPODOCUMENTO, FECHA_PRESEN, CD_LOC_ORIGEN, CD_PROV_ORIGEN, CD_DIV_ORIGEN, CD_ORG_ORIGEN, CD_LOC_DESTINO, CD_PROV_DESTINO, CD_DIV_DESTINO, CD_ORG_DESTINO, NUM_DOCUMENTOS, NUM_COMPULSAS, NUM_REC, NUM_RECEPCION, RESUMEN_INTER, CD_ESTADO, DESCRIPCION, OBS, CD_TIPOASUNTO_COM, ID_REGISTRO_SIR)
        (EJERCICIO, TIPOES, NUM_REGISTRO, FECHA, FECHAP, CD_OFICINA, NUM_REGISTROOF, USUARIO, CD_DIVISION_DESTINO, CD_ORGANO_DESTINO, CD_DIVISION_ORIGEN, CD_ORGANO_ORIGEN, CD_DEP_ORIGEN, EJERCICIO_REGIS_ORIGINAL, CD_REGIS_ORIGINAL, TIPO_REGIS_ORIGINAL, NUM_REGIS_ORIGINAL, FECHA_REGIS_ORIGINAL, TIPO_TRANSPORTE, NUM_TRANSPORTE, RESUMEN, DESCRIPTORES, UNIDAD_REGISTRAL, CD_DEP_DESTINO, PERSONA, FECHAU, CD_EJERCICIOEXP, CD_EXPEDIENTE, CD_TIPO, OFERTA, EXPOFERTA, ESTADO, ASUNTO, USUARIOU, OBSERVACIONES, NUM_SOBRES, NACEXT, EMBAJADA, CD_TIPODOCUMENTO, FECHA_PRESEN, CD_LOC_ORIGEN, CD_PROV_ORIGEN, CD_DIV_ORIGEN, CD_ORG_ORIGEN, CD_LOC_DESTINO, CD_PROV_DESTINO, CD_DIV_DESTINO, CD_ORG_DESTINO, NUM_DOCUMENTOS, NUM_COMPULSAS, NUM_REC, NUM_RECEPCION, RESUMEN_INTER, CD_ESTADO, DESCRIPCION, OBS, CD_TIPOASUNTO_COM)
        --SELECT EJERCICIO, TIPOES, NUM_REGISTRO, FECHA, FECHAP, CD_OFICINA, NUM_REGISTROOF, USUARIO, CD_DIVISION_DESTINO, CD_ORGANO_DESTINO, CD_DIVISION_ORIGEN, CD_ORGANO_ORIGEN, CD_DEP_ORIGEN, EJERCICIO_REGIS_ORIGINAL, CD_REGIS_ORIGINAL, TIPO_REGIS_ORIGINAL, NUM_REGIS_ORIGINAL, FECHA_REGIS_ORIGINAL, TIPO_TRANSPORTE, NUM_TRANSPORTE, RESUMEN, DESCRIPTORES, UNIDAD_REGISTRAL, CD_DEP_DESTINO, PERSONA, FECHAU, CD_EJERCICIOEXP, CD_EXPEDIENTE, CD_TIPO, OFERTA, EXPOFERTA, ESTADO, ASUNTO, USUARIOU, OBSERVACIONES, NUM_SOBRES, NACEXT, EMBAJADA, CD_TIPODOCUMENTO, FECHA_PRESEN, CD_LOC_ORIGEN, CD_PROV_ORIGEN, CD_DIV_ORIGEN, CD_ORG_ORIGEN, CD_LOC_DESTINO, CD_PROV_DESTINO, CD_DIV_DESTINO, CD_ORG_DESTINO, NUM_DOCUMENTOS, NUM_COMPULSAS, NUM_REC, NUM_RECEPCION, RESUMEN_INTER, CD_ESTADO, DESCRIPCION, OBS, CD_TIPOASUNTO_COM, ID_REGISTRO_SIR
        SELECT EJERCICIO, TIPOES, NUM_REGISTRO, FECHA, FECHAP, CD_OFICINA, NUM_REGISTROOF, USUARIO, CD_DIVISION_DESTINO, CD_ORGANO_DESTINO, CD_DIVISION_ORIGEN, CD_ORGANO_ORIGEN, CD_DEP_ORIGEN, EJERCICIO_REGIS_ORIGINAL, CD_REGIS_ORIGINAL, TIPO_REGIS_ORIGINAL, NUM_REGIS_ORIGINAL, FECHA_REGIS_ORIGINAL, TIPO_TRANSPORTE, NUM_TRANSPORTE, RESUMEN, DESCRIPTORES, UNIDAD_REGISTRAL, CD_DEP_DESTINO, PERSONA, FECHAU, CD_EJERCICIOEXP, CD_EXPEDIENTE, CD_TIPO, OFERTA, EXPOFERTA, ESTADO, ASUNTO, USUARIOU, OBSERVACIONES, NUM_SOBRES, NACEXT, EMBAJADA, CD_TIPODOCUMENTO, FECHA_PRESEN, CD_LOC_ORIGEN, CD_PROV_ORIGEN, CD_DIV_ORIGEN, CD_ORG_ORIGEN, CD_LOC_DESTINO, CD_PROV_DESTINO, CD_DIV_DESTINO, CD_ORG_DESTINO, NUM_DOCUMENTOS, NUM_COMPULSAS, NUM_REC, NUM_RECEPCION, RESUMEN_INTER, CD_ESTADO, DESCRIPCION, OBS, CD_TIPOASUNTO_COM
        FROM REGISTROES
        WHERE FECHA BETWEEN fv AND ff;
        --WHERE trunc(FECHAP) BETWEEN fv - 1 AND ff + 1;
        COMMIT;
        */

        OPEN ret_cursor FOR

        SELECT DISTINCT
            fv AS fechai,ff AS fechaf,
            ES.Num_RegistroOF,
            (CASE
           WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
                                RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                        AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
              THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
                    TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
                                RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                                AND RG.Num_Registro = ES.Num_RegistroOF))
                ELSE TO_CHAR(ES.Num_RegistroOF)
            END) AS NumRegistro,
            --ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
            --ES.FECHA_REGIS_ORIGINAL AS Fecharem,
            OFI.DS_Oficina,
            ES.FechaP AS Fecha,
            --DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
            US.DS_Usuario,
            (CASE
           WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
             THEN CASE
                    WHEN OG.CD_Organosup IS NULL
                            THEN DEP.DS_Departamento || '.' || OG.DS_Organo ||
                           NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                       RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                         ELSE DEP.DS_Departamento || '.' || OG.DS_Organo ||
                            ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                     WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                                      AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                           NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                       RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                        END
             ELSE
                       CASE
                    WHEN ES.CD_Org_Origen IS NOT NULL
                        THEN
                        CASE WHEN OG.CD_Organosup IS NULL
                            THEN OG.DS_Organo ||
                                   NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                         RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                          ELSE OG.DS_Organo || ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                                        WHERE    OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                                                         AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                               NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                           RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                                  END
                          ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                  RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                      END
               END) AS OrgOrigen,
          /*
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
            (SELECT MAX(LOCALIDAD) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                END AS LOCALIDAD,
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
            (SELECT MAX(PAIS) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                END AS PAIS,
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
                (SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
              (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
            END AS PROVINCIA,
          */
            ES.CD_Org_Destino AS CodOrganoD,
          ES.Cd_Div_Destino,
          (CASE WHEN ES.CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL AND
                     (SELECT DISTINCT T.CD_Tramite
                      FROM RegistroESTra T
                      WHERE ES.Ejercicio = T.Ejercicio
                            AND ES.TipoES = T.TipoES
                            AND ES.Num_Registro = T.Num_Registro
                            AND T.CD_tramite = 'RevisarORG') IS NULL
                THEN CASE WHEN OG1.CD_Organosup IS NULL
                    THEN DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo
                    ELSE DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
               (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                        WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                 AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
                    END
            ELSE
                CASE WHEN ES.CD_Org_Destino IS NOT NULL
                    THEN CASE WHEN OG1.CD_Organosup IS NULL
                           THEN ES.CD_Org_Destino || ' ' || OG1.DS_Organo
                           ELSE ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
                      (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                      AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')'
                           END
                    ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                  END
            END) AS OrgDestino,

             (select rel1.direccion || ' ' || rel1.cp || ' ' || loc1.ds_localidad
            from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
            where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
             and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
             and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as DireccionOrgDestino,


          (select loc1.cd_provincia
            from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
            where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
             and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
             and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as CodigoProvincia,

            --' /' || ES.CD_Regis_Original AS CD_Regis_Original,
            --DECODE(ES.Tipo_Regis_Original,'E',' / Entrada','S',' / Salida','') AS Tipo_Regis_original,
            --ES.Num_Regis_Original,
            --ES.Fecha_Regis_Original,
            --ES.Tipo_Transporte,
            --TR.DS_Transporte,
            --ES.Num_Transporte,
            NVL(CM.CONTENIDO,'') || ' ' || ES.Resumen ||'. '|| ES.Obs as Resumen, -- Incidencia 10529. Se junta el resumen con las observaciones
            --ES.ExpOferta,
            --OFI.Direccion,
            --OFI.CP,
            --OFI.Poblacion,
            OFI.Direccion || ' ' ||
            OFI.CP || ' ' ||
          (select loc2.DS_Localidad from AUX_LOCALIDADES loc2
           where loc2.CD_Provincia = OFI.CD_PROVINCIA and loc2.CD_Localidad = OFI.Cd_Localidad) as DireccionOficina,
            --OFI.Telefono,
            --OFI.FAX,
            --ES.ExpOferta,

          -- Dani 09/05/2007
            --(select c.DS_Logo
          -- from Dat_Config c inner join USUARIOS uu on
          --  uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = v)
          -- as Organismo
            (select c.DS_Logo from Dat_Config c where CD_Organismo = 5) as Organismo,
            CASE WHEN v_ta IS NOT NULL
               THEN CM.DS_COMENTARIO
               ELSE ''
            END AS TipoAsunto


        FROM
            AUX_OFICINAS OFI, AUX_OFICINAS OFI2, TT_REGISTROES2 ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1, AUX_TRANSPORTES TR,
          UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
            AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, REGISTROESTRA EST, AUX_COMENTARIOS CM
        WHERE
            EST.CD_Oficina = OFI.CD_Oficina
            AND (ES.CD_Div_Origen=OG.CD_Division(+))
            AND (ES.CD_Org_Origen=OG.CD_Organo(+))
            AND ES.CD_Org_Origen=DEP.CD_Organo(+)
            AND ES.CD_Dep_Origen=DEP.CD_Departamento(+)
            AND ES.CD_Org_Destino=DEP1.CD_Organo(+)
            AND ES.CD_Dep_Destino=DEP1.CD_Departamento(+)
            AND (ES.CD_Div_Destino = OG1.CD_Division)
            AND (ES.CD_Org_Destino = OG1.CD_Organo)
            AND ES.Tipo_Transporte=TR.CD_Transporte(+)
            AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
            AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
            AND ES.Usuario = US.DNI
            AND OFI.CD_Oficina = p
            AND ES.TipoES = 'E'
            AND ES.Ejercicio = EST.Ejercicio
            AND ES.TipoES = EST.TipoES
            AND ES.Num_Registro = EST.Num_Registro
            AND EST.CD_Tramite = 'EnviarRegistro'
          --AND (trunc(ES.FEchaP) >= fv and trunc(ES.FechaP) <= ff) --Fecha Registro
            --AND (fv IS NOT NULL OR ff IS NOT NULL)
            AND (ES.Num_RegistroOF >= s OR s = 0)
            AND (ES.Num_RegistroOF <= t OR t = 0)
            AND ( (ES.CD_Org_Destino  = u OR u is null)
                    OR
                ((ES.CD_Org_Destino IN
                     (SELECT CD_Organo FROM AUX_ORGANOS_PROD WHERE CD_Oficina =
                         (SELECT CD_Oficina FROM AUX_ORGANOS_PROD WHERE CD_Organo = u and CD_Division = w)) OR u is null)))
          ----
          AND (ES.CD_DIV_DESTINO = w OR w = 0)
          ----
            AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO

          -- Dani 09/05/2007
            --AND UUU.CD_USUARIO = v
            AND (UUU.CD_USUARIO = v OR v is null)

            AND ES.Estado IS NULL
          AND ES.Usuario = UUU.DNI
          AND (DEP1.CD_Departamento=y OR y is NULL)
          AND OG1.CD_Oficina = OFI2.CD_Oficina (+)
          AND ((v_ta IS NOT NULL AND TO_CHAR(ES.CD_TIPOASUNTO_COM) = v_ta) OR
                       (v_ta is null))
          --AND ((V_Z=0 and EST.cd_departamento is null) OR (V_Z=1 and EST.cd_departamento is not null))
          AND ((V_Z=0 and ES.CD_DEP_DESTINO is null) OR (V_Z=1 and ES.CD_DEP_DESTINO is not null))

        UNION
        SELECT DISTINCT
            fv AS fechai,ff AS fechaf,
            ES.Num_RegistroOF,
            (CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
                        RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                        AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
                THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
                     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
                        RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                        AND RG.Num_Registro = ES.Num_RegistroOF))
                ELSE TO_CHAR(ES.Num_RegistroOF)
                END) AS NumRegistro,
            --ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
            --ES.FECHA_REGIS_ORIGINAL AS Fecharem,
            OFI.DS_Oficina,
            ES.FechaP AS Fecha,
            --DECODE(ES.TipoES, 'E', 'Entrada', 'S', 'Salida', '') AS TipoES,
            US.DS_Usuario,
            (CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
                THEN CASE WHEN OG.CD_Organosup IS NULL
                    THEN    DEP.DS_Departamento || '.' || OG.DS_Organo ||
                        NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                            RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                    ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
              (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                        WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                 AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                        NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                            RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                    END
            ELSE
                CASE WHEN ES.CD_Org_Origen IS NOT NULL
                    THEN CASE WHEN OG.CD_Organosup IS NULL
                        THEN    OG.DS_Organo ||
                            NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                        ELSE  OG.DS_Organo || ' (' ||
                (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                            WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                  AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                            NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                        END
                    ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                END
            END) AS OrgOrigen,
          /*
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
            (SELECT MAX(LOCALIDAD) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                END AS LOCALIDAD,
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
            (SELECT MAX(PAIS) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                END AS PAIS,
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
                (SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
              (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
            END AS PROVINCIA,
          */
            EST.CD_Oficina AS CodOrganoD,
          ES.Cd_Div_Destino,
            --(SELECT AUX_OFICINAS.DS_Oficina FROM AUX_OFICINAS WHERE AUX_OFICINAS.CD_Oficina = EST.CD_Oficina) AS OrgDestino,

        (CASE WHEN ES.CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL AND
                     (SELECT DISTINCT T.CD_Tramite
                      FROM RegistroESTra T
                      WHERE ES.Ejercicio = T.Ejercicio
                            AND ES.TipoES = T.TipoES
                            AND ES.Num_Registro = T.Num_Registro
                            AND T.CD_tramite = 'RevisarORG') IS NULL
                THEN CASE WHEN OG1.CD_Organosup IS NULL
                    THEN DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' - (' || OFI2.DS_Oficina || ')'
                    ELSE DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo ||
               (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                        WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                 AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' || ' - (' || OFI2.DS_Oficina || ')'
                    END
            ELSE
                CASE WHEN ES.CD_Org_Destino IS NOT NULL
                    THEN CASE WHEN OG1.CD_Organosup IS NULL
                           THEN ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' - (' || OFI2.DS_Oficina || ')'
                           ELSE ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
                      (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                      AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' || ' - (' || OFI2.DS_Oficina || ')'
                           END
                    ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                  END
            END) AS OrgDestino,

        --  (select max(ofi2.Direccion) || ' ' || max(ofi2.CP) || ' ' || max(loc3.DS_Localidad)
        --   from AUX_OFICINAS ofi2, AUX_LOCALIDADES loc3
        --   where loc3.CD_Provincia = ofi2.CD_PROVINCIA and loc3.CD_Localidad = ofi2.CD_Localidad
        --    and ofi2.Cd_Oficina = EST.CD_OFICINA) as DireccionOrgDestino,


             (select rel1.direccion || ' ' || rel1.cp || ' ' || loc1.ds_localidad
            from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
            where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
             and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
             and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as DireccionOrgDestino,

          (select loc1.cd_provincia
            from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
            where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
             and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
             and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as CodigoProvincia,

            --' /' ||ES.CD_Regis_Original AS CD_Regis_Original,
            --DECODE(ES.Tipo_Regis_Original,'E',' / Entrada','S',' / Salida','') AS Tipo_Regis_original,
            --ES.Num_Regis_Original,
            --ES.Fecha_Regis_Original,
            --ES.Tipo_Transporte,
            --TR.DS_Transporte,
            --ES.Num_Transporte,
            NVL(CM.CONTENIDO,'') || ' ' || ES.Resumen ||'. '|| ES.Obs as Resumen, -- Incidencia 10529. Se junta el resumen con las observaciones
            --ES.ExpOferta,
            --OFI.Direccion,
            --OFI.CP,
            --OFI.Poblacion,
            OFI.Direccion || ' ' ||
            OFI.CP || ' ' ||
          (select loc4.DS_Localidad from AUX_LOCALIDADES loc4
           where loc4.CD_Provincia = OFI.CD_PROVINCIA and loc4.CD_Localidad = OFI.Cd_Localidad) as DireccionOficina,
            --OFI.Telefono,
            --OFI.FAX,
            --ES.ExpOferta,

          -- Dani 09/05/2007
            --(select c.DS_Logo
          -- from Dat_Config c inner join USUARIOS uu on
          --  uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = v)
          -- as Organismo
            (select c.DS_Logo from Dat_Config c where CD_Organismo = 5) as Organismo,
            CASE WHEN v_ta IS NOT NULL
               THEN CM.DS_COMENTARIO
               ELSE ''
            END AS TipoAsunto


        FROM
        --    AUX_OFICINAS OFI, AUX_OFICINAS OFI2, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_TRANSPORTES TR, UsuariosV1 US,
        --    DAT_CONFIG CC, USUARIOS uuu,
        --    AUX_DEPARTAMENTOS DEP, REGISTROESTRA EST
            AUX_OFICINAS OFI, AUX_OFICINAS OFI2, TT_REGISTROES2 ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1, AUX_TRANSPORTES TR,
          UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
            AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, REGISTROESTRA EST, AUX_COMENTARIOS CM

        WHERE
            ES.CD_Oficina = OFI.CD_Oficina
            AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
            AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
            AND (ES.CD_Div_Origen=OG.CD_Division(+))
            AND (ES.CD_Org_Origen=OG.CD_Organo(+))
            AND (ES.CD_Org_Origen=DEP.CD_Organo(+))
            AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
            AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
            AND ES.Usuario = US.DNI
            AND OFI.CD_Oficina = p
            AND ES.TipoES = 'E'
            AND ES.Ejercicio = EST.Ejercicio
            AND ES.TipoES = EST.TipoES
            AND ES.Num_Registro = EST.Num_Registro
            AND EST.CD_Tramite = 'EnviarUnidad'
            --AND (TRUNC(ES.FechaP) >= fv and trunc(ES.FechaP) <= ff) --Fecha Registro
            --AND (fv IS NOT NULL OR ff IS NOT NULL)
            AND (ES.Num_RegistroOF >= s OR s = 0)
            AND (ES.Num_RegistroOF <= t OR t = 0)
            AND ( (ES.CD_Org_Destino  = u OR u is null)
                   OR ((ES.CD_Org_Destino IN
                    (SELECT CD_Organo FROM AUX_ORGANOS_PROD WHERE CD_Oficina =
                       (SELECT CD_Oficina FROM AUX_ORGANOS_PROD WHERE CD_Organo = u and CD_Division = w)) OR u is null)))
          ----
          AND (ES.CD_DIV_DESTINO = w OR w = 0)
          ----
            AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO

          -- Dani 09/05/2007
            --AND UUU.CD_USUARIO = v
            AND (UUU.CD_USUARIO = v OR v is null)

             AND ES.Estado IS NULL
          AND ES.Usuario = UUU.DNI
          AND OG1.CD_Oficina = OFI2.CD_Oficina (+)
          AND ES.CD_Org_Destino=DEP1.CD_Organo(+)
            AND ES.CD_Dep_Destino=DEP1.CD_Departamento(+)
            AND (ES.CD_Div_Destino = OG1.CD_Division)
            AND (ES.CD_Org_Destino = OG1.CD_Organo)
          AND (DEP1.CD_Departamento=y OR y is NULL)
          AND ((v_ta IS NOT NULL AND TO_CHAR(ES.CD_TIPOASUNTO_COM) = v_ta) OR
                       (v_ta is null))
          --AND ((V_Z=0 and EST.cd_departamento is null) OR (V_Z=1 and EST.cd_departamento is not null))
          AND ((V_Z=0 and ES.CD_DEP_DESTINO is null) OR (V_Z=1 and ES.CD_DEP_DESTINO is not null))

        ORDER BY  OrgDestino, Num_RegistroOF;--NumRegistro;
        --ORDER BY  OrgDestino, Num_RegistroOF;
        --ORDER BY  CodOrganoD, Num_RegistroOF;

    ELSE -- NO USA LA TABLA TEMPORAL...

          OPEN ret_cursor FOR

        SELECT DISTINCT
            fv AS fechai,ff AS fechaf,
            ES.Num_RegistroOF,
            (CASE
           WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
                                RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                        AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
              THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
                    TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
                                RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                                AND RG.Num_Registro = ES.Num_RegistroOF))
                ELSE TO_CHAR(ES.Num_RegistroOF)
            END) AS NumRegistro,
            --ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
            --ES.FECHA_REGIS_ORIGINAL AS Fecharem,
            OFI.DS_Oficina,
            ES.FechaP AS Fecha,
            --DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
            US.DS_Usuario,
            (CASE
           WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
             THEN CASE
                    WHEN OG.CD_Organosup IS NULL
                            THEN DEP.DS_Departamento || '.' || OG.DS_Organo ||
                           NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                       RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                         ELSE DEP.DS_Departamento || '.' || OG.DS_Organo ||
                            ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                     WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                                      AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                           NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                       RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                        END
             ELSE
                       CASE
                    WHEN ES.CD_Org_Origen IS NOT NULL
                        THEN
                        CASE WHEN OG.CD_Organosup IS NULL
                            THEN OG.DS_Organo ||
                                   NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                         RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                          ELSE OG.DS_Organo || ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                                        WHERE    OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                                                         AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                               NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                           RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                                  END
                          ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                  RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                      END
               END) AS OrgOrigen,
          /*
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
            (SELECT MAX(LOCALIDAD) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                END AS LOCALIDAD,
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
            (SELECT MAX(PAIS) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                END AS PAIS,
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
                (SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
              (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
            END AS PROVINCIA,
          */
            ES.CD_Org_Destino AS CodOrganoD,
          ES.Cd_Div_Destino,
          (CASE WHEN ES.CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL AND
                     (SELECT DISTINCT T.CD_Tramite
                      FROM RegistroESTra T
                      WHERE ES.Ejercicio = T.Ejercicio
                            AND ES.TipoES = T.TipoES
                            AND ES.Num_Registro = T.Num_Registro
                            AND T.CD_tramite = 'RevisarORG') IS NULL
                THEN CASE WHEN OG1.CD_Organosup IS NULL
                    THEN DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo
                    ELSE DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
               (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                        WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                 AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
                    END
            ELSE
                CASE WHEN ES.CD_Org_Destino IS NOT NULL
                    THEN CASE WHEN OG1.CD_Organosup IS NULL
                           THEN ES.CD_Org_Destino || ' ' || OG1.DS_Organo
                           ELSE ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
                      (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                      AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')'
                           END
                    ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                  END
            END) AS OrgDestino,

             (select rel1.direccion || ' ' || rel1.cp || ' ' || loc1.ds_localidad
            from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
            where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
             and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
             and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as DireccionOrgDestino,


          (select loc1.cd_provincia
            from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
            where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
             and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
             and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as CodigoProvincia,

            --' /' || ES.CD_Regis_Original AS CD_Regis_Original,
            --DECODE(ES.Tipo_Regis_Original,'E',' / Entrada','S',' / Salida','') AS Tipo_Regis_original,
            --ES.Num_Regis_Original,
            --ES.Fecha_Regis_Original,
            --ES.Tipo_Transporte,
            --TR.DS_Transporte,
            --ES.Num_Transporte,
            NVL(CM.CONTENIDO,'') || ' ' || ES.Resumen ||'. '|| ES.Obs as Resumen, -- Incidencia 10529. Se junta el resumen con las observaciones
            --ES.ExpOferta,
            --OFI.Direccion,
            --OFI.CP,
            --OFI.Poblacion,
            OFI.Direccion || ' ' ||
            OFI.CP || ' ' ||
          (select loc2.DS_Localidad from AUX_LOCALIDADES loc2
           where loc2.CD_Provincia = OFI.CD_PROVINCIA and loc2.CD_Localidad = OFI.Cd_Localidad) as DireccionOficina,
            --OFI.Telefono,
            --OFI.FAX,
            --ES.ExpOferta,

          -- Dani 09/05/2007
            --(select c.DS_Logo
          -- from Dat_Config c inner join USUARIOS uu on
          --  uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = v)
          -- as Organismo
            (select c.DS_Logo from Dat_Config c where CD_Organismo = 5) as Organismo,
            CASE WHEN v_ta IS NOT NULL
               THEN CM.DS_COMENTARIO
               ELSE ''
            END AS TipoAsunto


        FROM
            AUX_OFICINAS OFI, AUX_OFICINAS OFI2, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1, AUX_TRANSPORTES TR,
          UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
            AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, REGISTROESTRA EST, AUX_COMENTARIOS CM
        WHERE
            EST.CD_Oficina = OFI.CD_Oficina
            AND (ES.CD_Div_Origen=OG.CD_Division(+))
            AND (ES.CD_Org_Origen=OG.CD_Organo(+))
            AND ES.CD_Org_Origen=DEP.CD_Organo(+)
            AND ES.CD_Dep_Origen=DEP.CD_Departamento(+)
            AND ES.CD_Org_Destino=DEP1.CD_Organo(+)
            AND ES.CD_Dep_Destino=DEP1.CD_Departamento(+)
            AND (ES.CD_Div_Destino = OG1.CD_Division)
            AND (ES.CD_Org_Destino = OG1.CD_Organo)
            AND ES.Tipo_Transporte=TR.CD_Transporte(+)
            AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
            AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
            AND ES.Usuario = US.DNI
            AND OFI.CD_Oficina = p
            AND ES.TipoES = 'E'
            AND ES.Ejercicio = EST.Ejercicio
            AND ES.TipoES = EST.TipoES
            AND ES.Num_Registro = EST.Num_Registro
            AND EST.CD_Tramite = 'EnviarRegistro'
            --AND (trunc(ES.FEchaP) >= fv and trunc(ES.FechaP) <= ff) --Fecha Registro
            AND (ES.Fecha >= fv and ES.Fecha <= ff) --Fecha Registro
            --AND (fv IS NOT NULL OR ff IS NOT NULL)
            AND (ES.Num_RegistroOF >= s OR s = 0)
            AND (ES.Num_RegistroOF <= t OR t = 0)
            AND ( (ES.CD_Org_Destino  = u OR u is null)
                    OR
                ((ES.CD_Org_Destino IN
                     (SELECT CD_Organo FROM AUX_ORGANOS_PROD WHERE CD_Oficina =
                         (SELECT CD_Oficina FROM AUX_ORGANOS_PROD WHERE CD_Organo = u and CD_Division = w)) OR u is null)))
          ----
          AND (ES.CD_DIV_DESTINO = w OR w = 0)
          ----
            AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO

          -- Dani 09/05/2007
            --AND UUU.CD_USUARIO = v
            AND (UUU.CD_USUARIO = v OR v is null)

            AND ES.Estado IS NULL
          AND ES.Usuario = UUU.DNI
          AND (DEP1.CD_Departamento=y OR y is NULL)
          AND OG1.CD_Oficina = OFI2.CD_Oficina (+)
          AND ((v_ta IS NOT NULL AND TO_CHAR(ES.CD_TIPOASUNTO_COM) = v_ta) OR
                       (v_ta is null))
          --AND ((V_Z=0 and EST.cd_departamento is null) OR (V_Z=1 and EST.cd_departamento is not null))
          AND ((V_Z=0 and ES.CD_DEP_DESTINO is null) OR (V_Z=1 and ES.CD_DEP_DESTINO is not null))

        UNION
        SELECT DISTINCT
            fv AS fechai,ff AS fechaf,
            ES.Num_RegistroOF,
            (CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
                        RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                        AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
                THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
                     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
                        RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                        AND RG.Num_Registro = ES.Num_RegistroOF))
                ELSE TO_CHAR(ES.Num_RegistroOF)
                END) AS NumRegistro,
            --ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
            --ES.FECHA_REGIS_ORIGINAL AS Fecharem,
            OFI.DS_Oficina,
            ES.FechaP AS Fecha,
            --DECODE(ES.TipoES, 'E', 'Entrada', 'S', 'Salida', '') AS TipoES,
            US.DS_Usuario,
            (CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
                THEN CASE WHEN OG.CD_Organosup IS NULL
                    THEN    DEP.DS_Departamento || '.' || OG.DS_Organo ||
                        NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                            RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                    ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
              (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                        WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                 AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                        NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                            RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                    END
            ELSE
                CASE WHEN ES.CD_Org_Origen IS NOT NULL
                    THEN CASE WHEN OG.CD_Organosup IS NULL
                        THEN    OG.DS_Organo ||
                            NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                        ELSE  OG.DS_Organo || ' (' ||
                (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                            WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                  AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                            NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                                RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                        END
                    ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                END
            END) AS OrgOrigen,
          /*
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
            (SELECT MAX(LOCALIDAD) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                END AS LOCALIDAD,
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
            (SELECT MAX(PAIS) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                END AS PAIS,
            CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
            THEN
                (SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
              (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
            END AS PROVINCIA,
          */
            EST.CD_Oficina AS CodOrganoD,
          ES.Cd_Div_Destino,
            --(SELECT AUX_OFICINAS.DS_Oficina FROM AUX_OFICINAS WHERE AUX_OFICINAS.CD_Oficina = EST.CD_Oficina) AS OrgDestino,

        (CASE WHEN ES.CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL AND
                     (SELECT DISTINCT T.CD_Tramite
                      FROM RegistroESTra T
                      WHERE ES.Ejercicio = T.Ejercicio
                            AND ES.TipoES = T.TipoES
                            AND ES.Num_Registro = T.Num_Registro
                            AND T.CD_tramite = 'RevisarORG') IS NULL
                THEN CASE WHEN OG1.CD_Organosup IS NULL
                    THEN DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' - (' || OFI2.DS_Oficina || ')'
                    ELSE DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo ||
               (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                        WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                 AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' || ' - (' || OFI2.DS_Oficina || ')'
                    END
            ELSE
                CASE WHEN ES.CD_Org_Destino IS NOT NULL
                    THEN CASE WHEN OG1.CD_Organosup IS NULL
                           THEN ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' - (' || OFI2.DS_Oficina || ')'
                           ELSE ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
                      (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                      AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' || ' - (' || OFI2.DS_Oficina || ')'
                           END
                    ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
                  END
            END) AS OrgDestino,

        --  (select max(ofi2.Direccion) || ' ' || max(ofi2.CP) || ' ' || max(loc3.DS_Localidad)
        --   from AUX_OFICINAS ofi2, AUX_LOCALIDADES loc3
        --   where loc3.CD_Provincia = ofi2.CD_PROVINCIA and loc3.CD_Localidad = ofi2.CD_Localidad
        --    and ofi2.Cd_Oficina = EST.CD_OFICINA) as DireccionOrgDestino,


             (select rel1.direccion || ' ' || rel1.cp || ' ' || loc1.ds_localidad
            from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
            where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
             and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
             and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as DireccionOrgDestino,

          (select loc1.cd_provincia
            from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
            where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
             and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
             and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as CodigoProvincia,

            --' /' ||ES.CD_Regis_Original AS CD_Regis_Original,
            --DECODE(ES.Tipo_Regis_Original,'E',' / Entrada','S',' / Salida','') AS Tipo_Regis_original,
            --ES.Num_Regis_Original,
            --ES.Fecha_Regis_Original,
            --ES.Tipo_Transporte,
            --TR.DS_Transporte,
            --ES.Num_Transporte,
            NVL(CM.CONTENIDO,'') || ' ' || ES.Resumen ||'. '|| ES.Obs as Resumen, -- Incidencia 10529. Se junta el resumen con las observaciones
            --ES.ExpOferta,
            --OFI.Direccion,
            --OFI.CP,
            --OFI.Poblacion,
            OFI.Direccion || ' ' ||
            OFI.CP || ' ' ||
          (select loc4.DS_Localidad from AUX_LOCALIDADES loc4
           where loc4.CD_Provincia = OFI.CD_PROVINCIA and loc4.CD_Localidad = OFI.Cd_Localidad) as DireccionOficina,
            --OFI.Telefono,
            --OFI.FAX,
            --ES.ExpOferta,

          -- Dani 09/05/2007
            --(select c.DS_Logo
          -- from Dat_Config c inner join USUARIOS uu on
          --  uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = v)
          -- as Organismo
            (select c.DS_Logo from Dat_Config c where CD_Organismo = 5) as Organismo,
            CASE WHEN v_ta IS NOT NULL
               THEN CM.DS_COMENTARIO
               ELSE ''
            END AS TipoAsunto


        FROM
        --    AUX_OFICINAS OFI, AUX_OFICINAS OFI2, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_TRANSPORTES TR, UsuariosV1 US,
        --    DAT_CONFIG CC, USUARIOS uuu,
        --    AUX_DEPARTAMENTOS DEP, REGISTROESTRA EST
            AUX_OFICINAS OFI, AUX_OFICINAS OFI2, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1, AUX_TRANSPORTES TR,
          UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
            AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, REGISTROESTRA EST, AUX_COMENTARIOS CM

        WHERE
            ES.CD_Oficina = OFI.CD_Oficina
            AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
            AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
            AND (ES.CD_Div_Origen=OG.CD_Division(+))
            AND (ES.CD_Org_Origen=OG.CD_Organo(+))
            AND (ES.CD_Org_Origen=DEP.CD_Organo(+))
            AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
            AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
            AND ES.Usuario = US.DNI
            AND OFI.CD_Oficina = p
            AND ES.TipoES = 'E'
            AND ES.Ejercicio = EST.Ejercicio
            AND ES.TipoES = EST.TipoES
            AND ES.Num_Registro = EST.Num_Registro
            AND EST.CD_Tramite = 'EnviarUnidad'
            --AND (TRUNC(ES.FechaP) >= fv and trunc(ES.FechaP) <= ff) --Fecha Registro
            AND (ES.Fecha >= fv and ES.Fecha <= ff) --Fecha Registro
            --AND (fv IS NOT NULL OR ff IS NOT NULL)
            AND (ES.Num_RegistroOF >= s OR s = 0)
            AND (ES.Num_RegistroOF <= t OR t = 0)
            AND ( (ES.CD_Org_Destino  = u OR u is null)
                   OR ((ES.CD_Org_Destino IN
                    (SELECT CD_Organo FROM AUX_ORGANOS_PROD WHERE CD_Oficina =
                       (SELECT CD_Oficina FROM AUX_ORGANOS_PROD WHERE CD_Organo = u and CD_Division = w)) OR u is null)))
          ----
          AND (ES.CD_DIV_DESTINO = w OR w = 0)
          ----
            AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO

          -- Dani 09/05/2007
            --AND UUU.CD_USUARIO = v
            AND (UUU.CD_USUARIO = v OR v is null)

             AND ES.Estado IS NULL
          AND ES.Usuario = UUU.DNI
          AND OG1.CD_Oficina = OFI2.CD_Oficina (+)
          AND ES.CD_Org_Destino=DEP1.CD_Organo(+)
            AND ES.CD_Dep_Destino=DEP1.CD_Departamento(+)
            AND (ES.CD_Div_Destino = OG1.CD_Division)
            AND (ES.CD_Org_Destino = OG1.CD_Organo)
          AND (DEP1.CD_Departamento=y OR y is NULL)
          AND ((v_ta IS NOT NULL AND TO_CHAR(ES.CD_TIPOASUNTO_COM) = v_ta) OR
                       (v_ta is null))
          --AND ((V_Z=0 and EST.cd_departamento is null) OR (V_Z=1 and EST.cd_departamento is not null))
          AND ((V_Z=0 and ES.CD_DEP_DESTINO is null) OR (V_Z=1 and ES.CD_DEP_DESTINO is not null))

        ORDER BY  OrgDestino, Num_RegistroOF;--NumRegistro;
        --ORDER BY  OrgDestino, Num_RegistroOF;
        --ORDER BY  CodOrganoD, Num_RegistroOF;

    END IF;
  cursorOut:=ret_cursor;


END IF;

END usp_RPT_IndicesE_fechas_MAP;

/*************************************************************************************************/

PROCEDURE usp_RPT_IndicesE_FPre_2200
  (p IN VARCHAR2, kk IN VARCHAR2, cc IN VARCHAR2,
   s IN INTEGER, t IN INTEGER, u IN VARCHAR2,
   v IN VARCHAR2, w IN INTEGER, x IN INTEGER,
   y IN VARCHAR2, z IN VARCHAR2, -- z concatena mostrar suborganos y el tipo asunto
   cursorOut OUT t_cursor)
IS
  ret_cursor t_cursor;

  fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
  ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

-- Proceso para separar la cadena
            TYPE registro IS RECORD
            (parametro VARCHAR2(500));


            TYPE v_registro IS TABLE OF registro
                 INDEX BY BINARY_INTEGER;

            vr_registro v_registro;

            v_cadena VARCHAR2(1000);
            v_contador NUMBER(4);
            CONT NUMBER(4);
            POS  NUMBER(4);

			V_Z NUMBER(4);  -- v_z=1 Muestra Subórganos, z=0 No los muestra
			V_TA NUMBER(4); -- Tipo Asunto

BEGIN

-- Inicio del proceso de separación de la cadena (z)
            v_cadena := z;

            -- Inicializamos el registro
            FOR CONT IN  0..7 LOOP
            	vr_registro(CONT).parametro:='';
            END LOOP;
   DBMS_OUTPUT.PUT_LINE ('paso 0');
            -- Separamos los parámetros que vienen en la nueva cadena de entrada
            v_contador:= 0;
            WHILE LENGTH(v_cadena) > 0 LOOP

            	IF INSTR(v_cadena,'~') != 0 THEN
            	    POS := INSTR(v_cadena,'~') ;
            	    vr_registro(v_contador).parametro := SUBSTR(v_cadena,1,POS-1);

            --		dbms_output.put_line( 'POS '|| POS);
            		v_cadena := SUBSTR(v_cadena, POS+1);
            		v_contador := v_contador + 1;

            	ELSE
            	   vr_registro(v_contador).parametro := v_cadena;
            	   v_contador := v_contador + 1;
            	   v_cadena := '';
            	END IF;

            END LOOP;

            -- Visualizacion del registro (todos los parámetros)

   DBMS_OUTPUT.PUT_LINE ('vr_registro(0).parametro: '||vr_registro(0).parametro);
   DBMS_OUTPUT.PUT_LINE ('vr_registro(1).parametro: '||vr_registro(1).parametro);

   V_Z:= TO_NUMBER (vr_registro(0).parametro);
   V_TA:= TO_NUMBER (vr_registro(1).parametro);

IF x=0 THEN

/* Si en el formulario NO se marca el checkbox de 'Usar Fecha Registro', se realiza la select que
 había en un principio. En caso de que se marque se modifica el where usando la fecha del registro
 en lugar de la fecha de envío*/

  OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE
   WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				        RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
	  THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		    TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
        				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				        AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
	END) AS NumRegistro,
	--ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	--ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.Fecha_presen AS Fecha,
	--DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
	(CASE
   WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
     THEN CASE
            WHEN OG.CD_Organosup IS NULL
			        THEN DEP.DS_Departamento || '.' || OG.DS_Organo ||
                   NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                   			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
             	ELSE DEP.DS_Departamento || '.' || OG.DS_Organo ||
                    ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                             WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                              AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                   NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                   			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			    END
     ELSE
     		  CASE
            WHEN ES.CD_Org_Origen IS NOT NULL
            	THEN
                CASE WHEN OG.CD_Organosup IS NULL
                	THEN OG.DS_Organo ||
                  		 NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                       		  RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                  ELSE OG.DS_Organo || ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                                WHERE	OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                                                 AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                       NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                       			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				          END
		          ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
              			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		      END
	   END) AS OrgOrigen,
  /*
	CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	THEN
	(SELECT MAX(LOCALIDAD) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS LOCALIDAD,
	CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	THEN
	(SELECT MAX(PAIS) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS PAIS,
	CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	THEN
		(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	END AS PROVINCIA,
  */
	ES.CD_Org_Destino AS CodOrganoD,
  ES.Cd_Div_Destino,

	(CASE WHEN ES.CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL AND
             (SELECT DISTINCT T.CD_Tramite
              FROM RegistroESTra T
              WHERE ES.Ejercicio = T.Ejercicio
                    AND ES.TipoES = T.TipoES
                    AND ES.Num_Registro = T.Num_Registro
                    AND T.CD_tramite = 'RevisarORG') IS NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo
			ELSE DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
       (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
         AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
			END
	ELSE
		CASE WHEN ES.CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				   --- Incluido dept destino
           THEN DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo
				   ELSE DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
           ---
              (SELECT DS_Organo FROM AUX_ORGANOS_PROD
					    WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
              AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')'
				   END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		  END
	END) AS OrgDestino,


 	(select rel1.direccion || ' ' || rel1.cp || ' ' || loc1.ds_localidad
    from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
    where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
     and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
     and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as DireccionOrgDestino,

  (select loc1.cd_provincia
    from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
    where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
     and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
     and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as CodigoProvincia,

	--' /' || ES.CD_Regis_Original AS CD_Regis_Original,
	--DECODE(ES.Tipo_Regis_Original,'E',' / Entrada','S',' / Salida','') AS Tipo_Regis_original,
	--ES.Num_Regis_Original,
	--ES.Fecha_Regis_Original,
	--ES.Tipo_Transporte,
	--TR.DS_Transporte,
	--ES.Num_Transporte,
	NVL(CM.CONTENIDO,'') || ' ' || ES.Resumen ||'. '|| ES.Obs as Resumen, -- Incidencia 10529. Se junta el resumen con las observaciones
	--ES.ExpOferta,
	--OFI.Direccion,
	--OFI.CP,
	--OFI.Poblacion,
	OFI.Direccion || ' ' ||
	OFI.CP || ' ' ||
  (select loc2.DS_Localidad from AUX_LOCALIDADES loc2
   where loc2.CD_Provincia = OFI.CD_PROVINCIA and loc2.CD_Localidad = OFI.Cd_Localidad) as DireccionOficina,
	--OFI.Telefono,
	--OFI.FAX,
	--ES.ExpOferta,

  -- Dani 09/05/2007
	--(select c.DS_Logo
  -- from Dat_Config c inner join USUARIOS uu on
  --  uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = v)
  -- as Organismo
	(select c.DS_Logo from Dat_Config c where CD_Organismo = 5) as Organismo,
	CASE WHEN v_ta IS NOT NULL
	   THEN CM.DS_COMENTARIO
	   ELSE ''
	END AS TipoAsunto


FROM
	AUX_OFICINAS OFI, AUX_OFICINAS OFI2, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1, AUX_TRANSPORTES TR,
  UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, REGISTROESTRA EST, AUX_COMENTARIOS CM

WHERE
	EST.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Div_Origen=OG.CD_Division(+))
	AND (ES.CD_Org_Origen=OG.CD_Organo(+))
	AND ES.CD_Org_Origen=DEP.CD_Organo(+)
	AND ES.CD_Dep_Origen=DEP.CD_Departamento(+)
	AND ES.CD_Org_Destino=DEP1.CD_Organo(+)
	AND ES.CD_Dep_Destino=DEP1.CD_Departamento(+)
	AND (ES.CD_Div_Destino = OG1.CD_Division)
	AND (ES.CD_Org_Destino = OG1.CD_Organo)
	AND ES.Tipo_Transporte=TR.CD_Transporte(+)
	AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
	AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarRegistro'
  --AND (TRUNC(ES.Fecha_presen) >= fv and trunc(ES.Fecha_presen) <= ff) --Fecha Envío
  AND (ES.Fecha_Presen_SH >= fv and ES.Fecha_Presen_SH <= ff) --Fecha Registro
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	AND ( (ES.CD_Org_Destino  = u OR u is null)
		    OR
        ((ES.CD_Org_Destino IN
        	 (SELECT CD_Organo FROM AUX_ORGANOS_PROD WHERE CD_Oficina =
           	  (SELECT CD_Oficina FROM AUX_ORGANOS_PROD WHERE CD_Organo = u and CD_Division = w)) OR u is null)))
  ----
  AND (ES.CD_DIV_DESTINO = w OR w = 0)
  ----
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO

  -- Dani 09/05/2007
	--AND UUU.CD_USUARIO = v
	AND (UUU.CD_USUARIO = v OR v is null)

	AND ES.Estado IS NULL
  AND ES.Usuario = UUU.DNI
  AND (DEP1.CD_Departamento=y OR y is NULL)
  AND OG1.CD_Oficina = OFI2.CD_Oficina (+)
  AND ((v_ta IS NOT NULL AND TO_CHAR(ES.CD_TIPOASUNTO_COM) = v_ta) OR
               (v_ta is null))
  --AND ((V_Z=0 and EST.cd_departamento is null) OR (V_Z=1 and EST.cd_departamento is not null))
  AND ((V_Z=0 and ES.CD_DEP_DESTINO is null) OR (V_Z=1 and ES.CD_DEP_DESTINO is not null))

UNION
SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	--ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	--ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.Fecha_presen AS Fecha,
	--DECODE(ES.TipoES, 'E', 'Entrada', 'S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
	(CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
         AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG.DS_Organo || ' (' ||
        (SELECT DS_Organo FROM AUX_ORGANOS_PROD
					WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
          AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
  /*
	CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	THEN
	(SELECT MAX(LOCALIDAD) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS LOCALIDAD,
	CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	THEN
	(SELECT MAX(PAIS) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS PAIS,
	CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	THEN
		(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	END AS PROVINCIA,
  */
	EST.CD_Oficina AS CodOrganoD,
  ES.Cd_Div_Destino,
	--(SELECT AUX_OFICINAS.DS_Oficina FROM AUX_OFICINAS WHERE AUX_OFICINAS.CD_Oficina = EST.CD_Oficina) AS OrgDestino,
(CASE WHEN ES.CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL AND
             (SELECT DISTINCT T.CD_Tramite
              FROM RegistroESTra T
              WHERE ES.Ejercicio = T.Ejercicio
                    AND ES.TipoES = T.TipoES
                    AND ES.Num_Registro = T.Num_Registro
                    AND T.CD_tramite = 'RevisarORG') IS NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' - (' || OFI2.DS_Oficina || ')'
			ELSE DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
       (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
         AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' || ' - (' || OFI2.DS_Oficina || ')'
			END
	ELSE
		CASE WHEN ES.CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				   THEN ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' - (' || OFI2.DS_Oficina || ')'
				   ELSE ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
              (SELECT DS_Organo FROM AUX_ORGANOS_PROD
					    WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
              AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' || ' - (' || OFI2.DS_Oficina || ')'
				   END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		  END
	END) AS OrgDestino,


--  (select max(ofi2.Direccion) || ' ' || max(ofi2.CP) || ' ' || max(loc3.DS_Localidad)
--   from AUX_OFICINAS ofi2, AUX_LOCALIDADES loc3
--   where loc3.CD_Provincia = ofi2.CD_PROVINCIA and loc3.CD_Localidad = ofi2.CD_Localidad
--    and ofi2.Cd_Oficina = EST.CD_OFICINA) as DireccionOrgDestino,

 	(select rel1.direccion || ' ' || rel1.cp || ' ' || loc1.ds_localidad
    from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
    where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
     and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
     and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as DireccionOrgDestino,

     (select loc1.cd_provincia
    from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
    where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
     and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
     and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as CodigoProvincia,

	--' /' ||ES.CD_Regis_Original AS CD_Regis_Original,
	--DECODE(ES.Tipo_Regis_Original,'E',' / Entrada','S',' / Salida','') AS Tipo_Regis_original,
	--ES.Num_Regis_Original,
	--ES.Fecha_Regis_Original,
	--ES.Tipo_Transporte,
	--TR.DS_Transporte,
	--ES.Num_Transporte,
    NVL(CM.CONTENIDO,'') || ' ' || ES.Resumen ||'. '|| ES.Obs as Resumen, -- Incidencia 10529. Se junta el resumen con las observaciones
	--ES.ExpOferta,
	--OFI.Direccion,
	--OFI.CP,
	--OFI.Poblacion,
	OFI.Direccion || ' ' ||
	OFI.CP || ' ' ||
  (select loc4.DS_Localidad from AUX_LOCALIDADES loc4
   where loc4.CD_Provincia = OFI.CD_PROVINCIA and loc4.CD_Localidad = OFI.Cd_Localidad) as DireccionOficina,
	--OFI.Telefono,
	--OFI.FAX,
	--ES.ExpOferta,

  -- Dani 09/05/2007
	--(select c.DS_Logo
  -- from Dat_Config c inner join USUARIOS uu on
  --  uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = v)
  -- as Organismo
	(select c.DS_Logo from Dat_Config c where CD_Organismo = 5) as Organismo,
	CASE WHEN v_ta IS NOT NULL
	   THEN CM.DS_COMENTARIO
	   ELSE ''
	END AS TipoAsunto


FROM
	--AUX_OFICINAS OFI, AUX_OFICINAS OFI2, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_TRANSPORTES TR, UsuariosV1 US,
	--DAT_CONFIG CC, USUARIOS uuu,
	--AUX_DEPARTAMENTOS DEP, REGISTROESTRA EST
	AUX_OFICINAS OFI, AUX_OFICINAS OFI2, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1, AUX_TRANSPORTES TR,
  UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, REGISTROESTRA EST, AUX_COMENTARIOS CM

WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Div_Origen=OG.CD_Division(+))
	AND (ES.CD_Org_Origen=OG.CD_Organo(+))
	AND (ES.CD_Org_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
	AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarUnidad'
	--AND (TRUNC(ES.Fecha_presen) >= fv and trunc(ES.Fecha_presen) <= ff) --Fecha Envío
  AND (ES.Fecha_Presen_SH >= fv and ES.Fecha_Presen_SH <= ff) --Fecha Registro
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	AND ( (ES.CD_Org_Destino  = u OR u is null)
		   OR ((ES.CD_Org_Destino IN
       	 (SELECT CD_Organo FROM AUX_ORGANOS_PROD WHERE CD_Oficina =
         	  (SELECT CD_Oficina FROM AUX_ORGANOS_PROD WHERE CD_Organo = u and CD_Division = w)) OR u is null)))
  ----
  AND (ES.CD_DIV_DESTINO = w OR w = 0)
  ----
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO

  -- Dani 09/05/2007
	--AND UUU.CD_USUARIO = v
	AND (UUU.CD_USUARIO = v OR v is null)

 	AND ES.Estado IS NULL
  AND ES.Usuario = UUU.DNI
  AND OG1.CD_Oficina = OFI2.CD_Oficina (+)
  AND ES.CD_Org_Destino=DEP1.CD_Organo(+)
	AND ES.CD_Dep_Destino=DEP1.CD_Departamento(+)
	AND (ES.CD_Div_Destino = OG1.CD_Division)
	AND (ES.CD_Org_Destino = OG1.CD_Organo)
  AND (DEP1.CD_Departamento=y OR y is NULL)
  AND ((v_ta IS NOT NULL AND TO_CHAR(ES.CD_TIPOASUNTO_COM) = v_ta) OR
               (v_ta is null))
  --AND ((V_Z=0 and EST.cd_departamento is null) OR (V_Z=1 and EST.cd_departamento is not null))
  AND ((V_Z=0 and ES.CD_DEP_DESTINO is null) OR (V_Z=1 and ES.CD_DEP_DESTINO is not null))

ORDER BY  OrgDestino, Num_RegistroOF; --NumRegistro;
--ORDER BY  OrgDestino, Num_RegistroOF;
--ORDER BY  CodOrganoD, Num_RegistroOF;

  cursorOut:=ret_cursor;

ELSE -- Se ha elegido la opción de Usar Fecha Registro ********************************************

  OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE
   WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				        RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
	  THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		    TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
        				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				        AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
	END) AS NumRegistro,
	--ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	--ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.Fecha_presen AS Fecha,
	--DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
	(CASE
   WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
     THEN CASE
            WHEN OG.CD_Organosup IS NULL
			        THEN DEP.DS_Departamento || '.' || OG.DS_Organo ||
                   NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                   			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
             	ELSE DEP.DS_Departamento || '.' || OG.DS_Organo ||
                    ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                             WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                              AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                   NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                   			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			    END
     ELSE
     		  CASE
            WHEN ES.CD_Org_Origen IS NOT NULL
            	THEN
                CASE WHEN OG.CD_Organosup IS NULL
                	THEN OG.DS_Organo ||
                  		 NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                       		  RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                  ELSE OG.DS_Organo || ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                                WHERE	OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                                                 AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                       NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                       			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				          END
		          ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
              			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		      END
	   END) AS OrgOrigen,
  /*
	CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	THEN
	(SELECT MAX(LOCALIDAD) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS LOCALIDAD,
	CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	THEN
	(SELECT MAX(PAIS) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS PAIS,
	CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	THEN
		(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	END AS PROVINCIA,
  */
	ES.CD_Org_Destino AS CodOrganoD,
  ES.Cd_Div_Destino,
  (CASE WHEN ES.CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL AND
             (SELECT DISTINCT T.CD_Tramite
              FROM RegistroESTra T
              WHERE ES.Ejercicio = T.Ejercicio
                    AND ES.TipoES = T.TipoES
                    AND ES.Num_Registro = T.Num_Registro
                    AND T.CD_tramite = 'RevisarORG') IS NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo
			ELSE DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
       (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
         AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
			END
	ELSE
		CASE WHEN ES.CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				   THEN ES.CD_Org_Destino || ' ' || OG1.DS_Organo
				   ELSE ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
              (SELECT DS_Organo FROM AUX_ORGANOS_PROD
					    WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
              AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')'
				   END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		  END
	END) AS OrgDestino,

 	(select rel1.direccion || ' ' || rel1.cp || ' ' || loc1.ds_localidad
    from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
    where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
     and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
     and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as DireccionOrgDestino,


  (select loc1.cd_provincia
    from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
    where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
     and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
     and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as CodigoProvincia,

	--' /' || ES.CD_Regis_Original AS CD_Regis_Original,
	--DECODE(ES.Tipo_Regis_Original,'E',' / Entrada','S',' / Salida','') AS Tipo_Regis_original,
	--ES.Num_Regis_Original,
	--ES.Fecha_Regis_Original,
	--ES.Tipo_Transporte,
	--TR.DS_Transporte,
	--ES.Num_Transporte,
	NVL(CM.CONTENIDO,'') || ' ' || ES.Resumen ||'. '|| ES.Obs as Resumen, -- Incidencia 10529. Se junta el resumen con las observaciones
	--ES.ExpOferta,
	--OFI.Direccion,
	--OFI.CP,
	--OFI.Poblacion,
	OFI.Direccion || ' ' ||
	OFI.CP || ' ' ||
  (select loc2.DS_Localidad from AUX_LOCALIDADES loc2
   where loc2.CD_Provincia = OFI.CD_PROVINCIA and loc2.CD_Localidad = OFI.Cd_Localidad) as DireccionOficina,
	--OFI.Telefono,
	--OFI.FAX,
	--ES.ExpOferta,

  -- Dani 09/05/2007
	--(select c.DS_Logo
  -- from Dat_Config c inner join USUARIOS uu on
  --  uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = v)
  -- as Organismo
	(select c.DS_Logo from Dat_Config c where CD_Organismo = 5) as Organismo,
	CASE WHEN v_ta IS NOT NULL
	   THEN CM.DS_COMENTARIO
	   ELSE ''
	END AS TipoAsunto


FROM
	AUX_OFICINAS OFI, AUX_OFICINAS OFI2, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1, AUX_TRANSPORTES TR,
  UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, REGISTROESTRA EST, AUX_COMENTARIOS CM
WHERE
	EST.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Div_Origen=OG.CD_Division(+))
	AND (ES.CD_Org_Origen=OG.CD_Organo(+))
	AND ES.CD_Org_Origen=DEP.CD_Organo(+)
	AND ES.CD_Dep_Origen=DEP.CD_Departamento(+)
	AND ES.CD_Org_Destino=DEP1.CD_Organo(+)
	AND ES.CD_Dep_Destino=DEP1.CD_Departamento(+)
	AND (ES.CD_Div_Destino = OG1.CD_Division)
	AND (ES.CD_Org_Destino = OG1.CD_Organo)
	AND ES.Tipo_Transporte=TR.CD_Transporte(+)
	AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
	AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarRegistro'
  --AND (trunc(ES.Fecha_presen) >= fv and trunc(ES.Fecha_presen) <= ff) --Fecha Registro
  AND (ES.Fecha_Presen_SH >= fv and ES.Fecha_Presen_SH <= ff) --Fecha Registro
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	AND ( (ES.CD_Org_Destino  = u OR u is null)
		    OR
        ((ES.CD_Org_Destino IN
        	 (SELECT CD_Organo FROM AUX_ORGANOS_PROD WHERE CD_Oficina =
           	  (SELECT CD_Oficina FROM AUX_ORGANOS_PROD WHERE CD_Organo = u and CD_Division = w)) OR u is null)))
  ----
  AND (ES.CD_DIV_DESTINO = w OR w = 0)
  ----
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO

  -- Dani 09/05/2007
	--AND UUU.CD_USUARIO = v
	AND (UUU.CD_USUARIO = v OR v is null)

	AND ES.Estado IS NULL
  AND ES.Usuario = UUU.DNI
  AND (DEP1.CD_Departamento=y OR y is NULL)
  AND OG1.CD_Oficina = OFI2.CD_Oficina (+)
  AND ((v_ta IS NOT NULL AND TO_CHAR(ES.CD_TIPOASUNTO_COM) = v_ta) OR
               (v_ta is null))
  --AND ((V_Z=0 and EST.cd_departamento is null) OR (V_Z=1 and EST.cd_departamento is not null))
  AND ((V_Z=0 and ES.CD_DEP_DESTINO is null) OR (V_Z=1 and ES.CD_DEP_DESTINO is not null))

UNION
SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	--ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	--ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.Fecha_presen AS Fecha,
	--DECODE(ES.TipoES, 'E', 'Entrada', 'S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
	(CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
         AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG.DS_Organo || ' (' ||
        (SELECT DS_Organo FROM AUX_ORGANOS_PROD
					WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
          AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
  /*
	CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	THEN
	(SELECT MAX(LOCALIDAD) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS LOCALIDAD,
	CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	THEN
	(SELECT MAX(PAIS) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS PAIS,
	CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	THEN
		(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	END AS PROVINCIA,
  */
	EST.CD_Oficina AS CodOrganoD,
  ES.Cd_Div_Destino,
	--(SELECT AUX_OFICINAS.DS_Oficina FROM AUX_OFICINAS WHERE AUX_OFICINAS.CD_Oficina = EST.CD_Oficina) AS OrgDestino,

(CASE WHEN ES.CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL AND
             (SELECT DISTINCT T.CD_Tramite
              FROM RegistroESTra T
              WHERE ES.Ejercicio = T.Ejercicio
                    AND ES.TipoES = T.TipoES
                    AND ES.Num_Registro = T.Num_Registro
                    AND T.CD_tramite = 'RevisarORG') IS NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' - (' || OFI2.DS_Oficina || ')'
			ELSE DECODE(V_Z,1,DEP1.DS_Departamento,'') || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo ||
       (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
         AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' || ' - (' || OFI2.DS_Oficina || ')'
			END
	ELSE
		CASE WHEN ES.CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				   THEN ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' - (' || OFI2.DS_Oficina || ')'
				   ELSE ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
              (SELECT DS_Organo FROM AUX_ORGANOS_PROD
					    WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
              AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' || ' - (' || OFI2.DS_Oficina || ')'
				   END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		  END
	END) AS OrgDestino,

--  (select max(ofi2.Direccion) || ' ' || max(ofi2.CP) || ' ' || max(loc3.DS_Localidad)
--   from AUX_OFICINAS ofi2, AUX_LOCALIDADES loc3
--   where loc3.CD_Provincia = ofi2.CD_PROVINCIA and loc3.CD_Localidad = ofi2.CD_Localidad
--    and ofi2.Cd_Oficina = EST.CD_OFICINA) as DireccionOrgDestino,


 	(select rel1.direccion || ' ' || rel1.cp || ' ' || loc1.ds_localidad
    from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
    where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
     and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
     and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as DireccionOrgDestino,

  (select loc1.cd_provincia
    from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
    where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
     and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
     and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as CodigoProvincia,

	--' /' ||ES.CD_Regis_Original AS CD_Regis_Original,
	--DECODE(ES.Tipo_Regis_Original,'E',' / Entrada','S',' / Salida','') AS Tipo_Regis_original,
	--ES.Num_Regis_Original,
	--ES.Fecha_Regis_Original,
	--ES.Tipo_Transporte,
	--TR.DS_Transporte,
	--ES.Num_Transporte,
	NVL(CM.CONTENIDO,'') || ' ' || ES.Resumen ||'. '|| ES.Obs as Resumen, -- Incidencia 10529. Se junta el resumen con las observaciones
	--ES.ExpOferta,
	--OFI.Direccion,
	--OFI.CP,
	--OFI.Poblacion,
	OFI.Direccion || ' ' ||
	OFI.CP || ' ' ||
  (select loc4.DS_Localidad from AUX_LOCALIDADES loc4
   where loc4.CD_Provincia = OFI.CD_PROVINCIA and loc4.CD_Localidad = OFI.Cd_Localidad) as DireccionOficina,
	--OFI.Telefono,
	--OFI.FAX,
	--ES.ExpOferta,

  -- Dani 09/05/2007
	--(select c.DS_Logo
  -- from Dat_Config c inner join USUARIOS uu on
  --  uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = v)
  -- as Organismo
	(select c.DS_Logo from Dat_Config c where CD_Organismo = 5) as Organismo,
	CASE WHEN v_ta IS NOT NULL
	   THEN CM.DS_COMENTARIO
	   ELSE ''
	END AS TipoAsunto


FROM
--	AUX_OFICINAS OFI, AUX_OFICINAS OFI2, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_TRANSPORTES TR, UsuariosV1 US,
--	DAT_CONFIG CC, USUARIOS uuu,
--	AUX_DEPARTAMENTOS DEP, REGISTROESTRA EST
	AUX_OFICINAS OFI, AUX_OFICINAS OFI2, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1, AUX_TRANSPORTES TR,
  UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, REGISTROESTRA EST, AUX_COMENTARIOS CM

WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
	AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
	AND (ES.CD_Div_Origen=OG.CD_Division(+))
	AND (ES.CD_Org_Origen=OG.CD_Organo(+))
	AND (ES.CD_Org_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarUnidad'
	--AND (TRUNC(ES.Fecha_presen) >= fv and trunc(ES.Fecha_presen) <= ff) --Fecha Registro
  AND (ES.Fecha_Presen_SH >= fv and ES.Fecha_Presen_SH <= ff) --Fecha Registro
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	AND ( (ES.CD_Org_Destino  = u OR u is null)
		   OR ((ES.CD_Org_Destino IN
       	 (SELECT CD_Organo FROM AUX_ORGANOS_PROD WHERE CD_Oficina =
         	  (SELECT CD_Oficina FROM AUX_ORGANOS_PROD WHERE CD_Organo = u and CD_Division = w)) OR u is null)))
  ----
  AND (ES.CD_DIV_DESTINO = w OR w = 0)
  ----
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO

  -- Dani 09/05/2007
	--AND UUU.CD_USUARIO = v
	AND (UUU.CD_USUARIO = v OR v is null)

 	AND ES.Estado IS NULL
  AND ES.Usuario = UUU.DNI
  AND OG1.CD_Oficina = OFI2.CD_Oficina (+)
  AND ES.CD_Org_Destino=DEP1.CD_Organo(+)
	AND ES.CD_Dep_Destino=DEP1.CD_Departamento(+)
	AND (ES.CD_Div_Destino = OG1.CD_Division)
	AND (ES.CD_Org_Destino = OG1.CD_Organo)
  AND (DEP1.CD_Departamento=y OR y is NULL)
  AND ((v_ta IS NOT NULL AND TO_CHAR(ES.CD_TIPOASUNTO_COM) = v_ta) OR
               (v_ta is null))
  --AND ((V_Z=0 and EST.cd_departamento is null) OR (V_Z=1 and EST.cd_departamento is not null))
  AND ((V_Z=0 and ES.CD_DEP_DESTINO is null) OR (V_Z=1 and ES.CD_DEP_DESTINO is not null))

ORDER BY  OrgDestino, Num_RegistroOF;--NumRegistro;
--ORDER BY  OrgDestino, Num_RegistroOF;
--ORDER BY  CodOrganoD, Num_RegistroOF;

  cursorOut:=ret_cursor;


END IF;

END usp_RPT_IndicesE_FPre_2200;

/*************************************************************************************************/

PROCEDURE usp_RPT_IndicesE_INT_MAP
(p IN VARCHAR2,--Oficina Solicitada
 kk IN VARCHAR2,--Fecha inicial
 cc IN VARCHAR2,--Fecha final
 s IN INTEGER, --reg inicial
 t IN INTEGER, --reg final
 r in VARCHAR2,--Oficina solicitante
 v IN VARCHAR2,--usuario
 x IN INTEGER, --check

 cursorOut OUT RPT.t_cursor)
IS
--OFINED44 // 17/06/2008 // 17/06/2008 // 0 // 0 // MAPINT // burke// 0
  ret_cursor RPT.t_cursor;

  fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
  ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

BEGIN

IF x=0 THEN

/* Si en el formulario NO se marca el checkbox de 'Usar Fecha Registro', se realiza la select que
 había en un principio. En caso de que se marque se modifica el where usando la fecha del registro
 en lugar de la fecha de envío*/

  OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE
   WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				        RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
	  THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		    TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
        				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				        AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
	END) AS NumRegistro,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha,
	US.DS_Usuario,
	(CASE
   WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
     THEN CASE
            WHEN OG.CD_Organosup IS NULL
			        THEN DEP.DS_Departamento || '.' || OG.DS_Organo ||
                   NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                   			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
             	ELSE DEP.DS_Departamento || '.' || OG.DS_Organo ||
                    ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                             WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                              AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                   NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                   			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			    END
     ELSE
     		  CASE
            WHEN ES.CD_Org_Origen IS NOT NULL
            	THEN
                CASE WHEN OG.CD_Organosup IS NULL
                	THEN OG.DS_Organo ||
                  		 NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                       		  RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                  ELSE OG.DS_Organo || ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                                WHERE	OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                                                 AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                       NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                       			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				          END
		          ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
              			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		      END
	   END) AS OrgOrigen,
  ES.CD_Org_Destino AS CodOrganoD,
  ES.Cd_Div_Destino,

	(CASE WHEN ES.CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL AND
             (SELECT DISTINCT T.CD_Tramite
              FROM RegistroESTra T
              WHERE ES.Ejercicio = T.Ejercicio
                    AND ES.TipoES = T.TipoES
                    AND ES.Num_Registro = T.Num_Registro
                    AND T.CD_tramite = 'RevisarORG') IS NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN DEP1.DS_Departamento || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo
			ELSE DEP1.DS_Departamento || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
       (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
         AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
			END
	ELSE
		CASE WHEN ES.CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				   --THEN ES.CD_Org_Destino || ' ' || OG1.DS_Organo
				   --ELSE ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
				   --- Incluido dept destino
           THEN DEP1.DS_Departamento || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo
				   ELSE DEP1.DS_Departamento || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
           ---
              (SELECT DS_Organo FROM AUX_ORGANOS_PROD
					    WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
              AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')'
				   END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		  END
	END) AS OrgDestino,


 	(select rel1.direccion || ' ' || rel1.cp || ' ' || loc1.ds_localidad
    from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
    where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
     and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
     and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as DireccionOrgDestino,

  (select loc1.cd_provincia
    from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
    where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
     and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
     and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as CodigoProvincia,
	ES.Resumen ||'. '|| ES.Obs as Resumen, -- Incidencia 10529. Se junta el resumen con las observaciones
	OFI.Direccion || ' ' ||
	OFI.CP || ' ' ||
  (select loc2.DS_Localidad from AUX_LOCALIDADES loc2
   where loc2.CD_Provincia = OFI.CD_PROVINCIA and loc2.CD_Localidad = OFI.Cd_Localidad) as DireccionOficina,
	(select c.DS_Logo from Dat_Config c where CD_Organismo = 5) as Organismo

FROM
	AUX_OFICINAS OFI, AUX_OFICINAS OFI2, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1, AUX_TRANSPORTES TR,
  UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, REGISTROESTRA EST

WHERE
	EST.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Div_Origen=OG.CD_Division(+))
	AND (ES.CD_Org_Origen=OG.CD_Organo(+))
	AND ES.CD_Org_Origen=DEP.CD_Organo(+)
	AND ES.CD_Dep_Origen=DEP.CD_Departamento(+)
	AND ES.CD_Org_Destino=DEP1.CD_Organo(+)
	AND ES.CD_Dep_Destino=DEP1.CD_Departamento(+)
	AND (ES.CD_Div_Destino = OG1.CD_Division)
	AND (ES.CD_Org_Destino = OG1.CD_Organo)
	AND ES.Tipo_Transporte=TR.CD_Transporte(+)
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = r
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarRegistro'
  --AND (TRUNC(EST.F_EFECTOS) >= fv and trunc(EST.F_EFECTOS) <= ff) --Fecha Envío
  AND (EST.FECHA_SH >= fv and EST.FECHA_SH <= ff) --Fecha Envío
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	/*AND (ES.CD_Org_Destino IN
        	(SELECT CD_Organodf_PROD FROM Aux_Oficinas
            WHERE CD_Oficina = r))
  ----
  AND (ES.CD_DIV_DESTINO IN
        	(SELECT CD_DIVISIONdf_PROD FROM Aux_Oficinas
            WHERE CD_Oficina = r))*/

  AND (ES.CD_Oficina = p or p is null)
  ----
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND (UUU.CD_USUARIO = v OR v is null)

	AND ES.Estado IS NULL
  AND ES.Usuario = UUU.DNI
  AND OG1.CD_Oficina = OFI2.CD_Oficina (+)

UNION
SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha,
	US.DS_Usuario,
	(CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
         AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG.DS_Organo || ' (' ||
        (SELECT DS_Organo FROM AUX_ORGANOS_PROD
					WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
          AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	EST.CD_Oficina AS CodOrganoD,
  ES.Cd_Div_Destino,
(CASE WHEN ES.CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL AND
             (SELECT DISTINCT T.CD_Tramite
              FROM RegistroESTra T
              WHERE ES.Ejercicio = T.Ejercicio
                    AND ES.TipoES = T.TipoES
                    AND ES.Num_Registro = T.Num_Registro
                    AND T.CD_tramite = 'RevisarORG') IS NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN DEP1.DS_Departamento || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' - (' || OFI2.DS_Oficina || ')'
			ELSE DEP1.DS_Departamento || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
       (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
         AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' || ' - (' || OFI2.DS_Oficina || ')'
			END
	ELSE
		CASE WHEN ES.CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				   THEN ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' - (' || OFI2.DS_Oficina || ')'
				   ELSE ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
              (SELECT DS_Organo FROM AUX_ORGANOS_PROD
					    WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
              AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' || ' - (' || OFI2.DS_Oficina || ')'
				   END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		  END
	END) AS OrgDestino,
 	(select rel1.direccion || ' ' || rel1.cp || ' ' || loc1.ds_localidad
    from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
    where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
     and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
     and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as DireccionOrgDestino,

     (select loc1.cd_provincia
    from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
    where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
     and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
     and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as CodigoProvincia,
  ES.Resumen ||'. '|| ES.Obs as Resumen, -- Incidencia 10529. Se junta el resumen con las observaciones
	OFI.Direccion || ' ' ||
	OFI.CP || ' ' ||
  (select loc4.DS_Localidad from AUX_LOCALIDADES loc4
   where loc4.CD_Provincia = OFI.CD_PROVINCIA and loc4.CD_Localidad = OFI.Cd_Localidad) as DireccionOficina,
	(select c.DS_Logo from Dat_Config c where CD_Organismo = 5) as Organismo

FROM
	AUX_OFICINAS OFI, AUX_OFICINAS OFI2, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1, AUX_TRANSPORTES TR,
  UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, REGISTROESTRA EST

WHERE
	EST.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Div_Origen=OG.CD_Division(+))
	AND (ES.CD_Org_Origen=OG.CD_Organo(+))
	AND (ES.CD_Org_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = r
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarUnidad'
	--AND (TRUNC(EST.F_EFECTOS) >= fv and trunc(EST.F_EFECTOS) <= ff) --Fecha Envío
  AND (EST.FECHA_SH >= fv and EST.FECHA_SH <= ff) --Fecha Envío
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	/*AND (ES.CD_Org_Destino IN
        	(SELECT CD_Organodf_PROD FROM Aux_Oficinas
            WHERE CD_Oficina = r))
  ----
  AND (ES.CD_DIV_DESTINO IN
        	(SELECT CD_DIVISIONdf_PROD FROM Aux_Oficinas
            WHERE CD_Oficina = r))*/
  AND (ES.CD_Oficina = p or p is null)
  ----
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND (UUU.CD_USUARIO = v OR v is null)

 	AND ES.Estado IS NULL
  AND ES.Usuario = UUU.DNI
  AND OG1.CD_Oficina = OFI2.CD_Oficina (+)
  AND ES.CD_Org_Destino=DEP1.CD_Organo(+)
	AND ES.CD_Dep_Destino=DEP1.CD_Departamento(+)
	AND (ES.CD_Div_Destino = OG1.CD_Division)
	AND (ES.CD_Org_Destino = OG1.CD_Organo)

ORDER BY  OrgDestino, Num_RegistroOF;

  cursorOut:=ret_cursor;

ELSE -- Se ha elegido la opción de Usar Fecha Registro ********************************************

  OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE
   WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				        RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
	  THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		    TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
        				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				        AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
	END) AS NumRegistro,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha,
	US.DS_Usuario,
	(CASE
   WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
     THEN CASE
            WHEN OG.CD_Organosup IS NULL
			        THEN DEP.DS_Departamento || '.' || OG.DS_Organo ||
                   NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                   			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
             	ELSE DEP.DS_Departamento || '.' || OG.DS_Organo ||
                    ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                             WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                              AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                   NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                   			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			    END
     ELSE
     		  CASE
            WHEN ES.CD_Org_Origen IS NOT NULL
            	THEN
                CASE WHEN OG.CD_Organosup IS NULL
                	THEN OG.DS_Organo ||
                  		 NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                       		  RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                  ELSE OG.DS_Organo || ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                                WHERE	OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                                                 AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                       NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                       			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				          END
		          ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
              			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		      END
	   END) AS OrgOrigen,
	ES.CD_Org_Destino AS CodOrganoD,
  ES.Cd_Div_Destino,
  (CASE WHEN ES.CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL AND
             (SELECT DISTINCT T.CD_Tramite
              FROM RegistroESTra T
              WHERE ES.Ejercicio = T.Ejercicio
                    AND ES.TipoES = T.TipoES
                    AND ES.Num_Registro = T.Num_Registro
                    AND T.CD_tramite = 'RevisarORG') IS NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN DEP1.DS_Departamento || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo
			ELSE DEP1.DS_Departamento || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
       (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
         AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
			END
	ELSE
		CASE WHEN ES.CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				   THEN ES.CD_Org_Destino || ' ' || OG1.DS_Organo
				   ELSE ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
              (SELECT DS_Organo FROM AUX_ORGANOS_PROD
					    WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
              AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')'
				   END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		  END
	END) AS OrgDestino,

 	(select rel1.direccion || ' ' || rel1.cp || ' ' || loc1.ds_localidad
    from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
    where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
     and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
     and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as DireccionOrgDestino,


  (select loc1.cd_provincia
    from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
    where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
     and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
     and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as CodigoProvincia,
	ES.Resumen ||'. '|| ES.Obs as Resumen, -- Incidencia 10529. Se junta el resumen con las observaciones
	OFI.Direccion || ' ' ||
	OFI.CP || ' ' ||
  (select loc2.DS_Localidad from AUX_LOCALIDADES loc2
   where loc2.CD_Provincia = OFI.CD_PROVINCIA and loc2.CD_Localidad = OFI.Cd_Localidad) as DireccionOficina,
	(select c.DS_Logo from Dat_Config c where CD_Organismo = 5) as Organismo

FROM
	AUX_OFICINAS OFI, AUX_OFICINAS OFI2, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1, AUX_TRANSPORTES TR,
  UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, REGISTROESTRA EST
WHERE
	EST.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Div_Origen=OG.CD_Division(+))
	AND (ES.CD_Org_Origen=OG.CD_Organo(+))
	AND ES.CD_Org_Origen=DEP.CD_Organo(+)
	AND ES.CD_Dep_Origen=DEP.CD_Departamento(+)
	AND ES.CD_Org_Destino=DEP1.CD_Organo(+)
	AND ES.CD_Dep_Destino=DEP1.CD_Departamento(+)
	AND (ES.CD_Div_Destino = OG1.CD_Division)
	AND (ES.CD_Org_Destino = OG1.CD_Organo)
	AND ES.Tipo_Transporte=TR.CD_Transporte(+)
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = r
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarRegistro'
  --AND (trunc(ES.FEchaP) >= fv and trunc(ES.FechaP) <= ff) --Fecha Registro
  AND (ES.Fecha >= fv and ES.Fecha <= ff) --Fecha Registro
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	/*AND (ES.CD_Org_Destino IN
        	(SELECT CD_Organodf_PROD FROM Aux_Oficinas
            WHERE CD_Oficina = r))
  ----
  AND (ES.CD_DIV_DESTINO IN
        	(SELECT CD_DIVISIONdf_PROD FROM Aux_Oficinas
            WHERE CD_Oficina = r))*/
  AND (ES.CD_Oficina = p or p is null)
  ----
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND (UUU.CD_USUARIO = v OR v is null)

	AND ES.Estado IS NULL
  AND ES.Usuario = UUU.DNI
  AND OG1.CD_Oficina = OFI2.CD_Oficina (+)


UNION
SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha,
	US.DS_Usuario,
	(CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
         AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG.DS_Organo || ' (' ||
        (SELECT DS_Organo FROM AUX_ORGANOS_PROD
					WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
          AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	EST.CD_Oficina AS CodOrganoD,
  ES.Cd_Div_Destino,

(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL AND
             (SELECT DISTINCT T.CD_Tramite
              FROM RegistroESTra T
              WHERE ES.Ejercicio = T.Ejercicio
                    AND ES.TipoES = T.TipoES
                    AND ES.Num_Registro = T.Num_Registro
                    AND T.CD_tramite = 'RevisarORG') IS NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN DEP1.DS_Departamento || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' - (' || OFI2.DS_Oficina || ')'
			ELSE DEP1.DS_Departamento || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo ||
       (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
         AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' || ' - (' || OFI2.DS_Oficina || ')'
			END
	ELSE
		CASE WHEN CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				   THEN ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' - (' || OFI2.DS_Oficina || ')'
				   ELSE ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
              (SELECT DS_Organo FROM AUX_ORGANOS_PROD
					    WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
              AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' || ' - (' || OFI2.DS_Oficina || ')'
				   END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		  END
	END) AS OrgDestino,
 	(select rel1.direccion || ' ' || rel1.cp || ' ' || loc1.ds_localidad
    from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
    where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
     and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
     and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as DireccionOrgDestino,

  (select loc1.cd_provincia
    from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
    where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
     and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
     and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as CodigoProvincia,
	ES.Resumen ||'. '|| ES.Obs as Resumen, -- Incidencia 10529. Se junta el resumen con las observaciones
	OFI.Direccion || ' ' ||
	OFI.CP || ' ' ||
  (select loc4.DS_Localidad from AUX_LOCALIDADES loc4
   where loc4.CD_Provincia = OFI.CD_PROVINCIA and loc4.CD_Localidad = OFI.Cd_Localidad) as DireccionOficina,
	(select c.DS_Logo from Dat_Config c where CD_Organismo = 5) as Organismo

FROM
	AUX_OFICINAS OFI, AUX_OFICINAS OFI2, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1, AUX_TRANSPORTES TR,
  UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, REGISTROESTRA EST

WHERE
	EST.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Div_Origen=OG.CD_Division(+))
	AND (ES.CD_Org_Origen=OG.CD_Organo(+))
	AND (ES.CD_Org_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = r
	AND ES.TipoES = 'E'
	AND ES.Ejercicio = EST.Ejercicio
	AND ES.TipoES = EST.TipoES
	AND ES.Num_Registro = EST.Num_Registro
	AND EST.CD_Tramite = 'EnviarUnidad'
	--AND (TRUNC(ES.FechaP) >= fv and trunc(ES.FechaP) <= ff) --Fecha Registro
  AND (ES.Fecha >= fv and ES.Fecha <= ff) --Fecha Registro
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	/*AND (ES.CD_Org_Destino IN
        	(SELECT CD_Organodf_PROD FROM Aux_Oficinas
            WHERE CD_Oficina = r))
  ----
  AND (ES.CD_DIV_DESTINO IN
        	(SELECT CD_DIVISIONdf_PROD FROM Aux_Oficinas
            WHERE CD_Oficina = r))*/
  AND (ES.CD_Oficina = p or p is null)
  ----
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND (UUU.CD_USUARIO = v OR v is null)

 	AND ES.Estado IS NULL
  AND ES.Usuario = UUU.DNI
  AND OG1.CD_Oficina = OFI2.CD_Oficina (+)
  AND ES.CD_Org_Destino=DEP1.CD_Organo(+)
	AND ES.CD_Dep_Destino=DEP1.CD_Departamento(+)
	AND (ES.CD_Div_Destino = OG1.CD_Division)
	AND (ES.CD_Org_Destino = OG1.CD_Organo)

ORDER BY  OrgDestino, Num_RegistroOF;

  cursorOut:=ret_cursor;


END IF;

END usp_RPT_IndicesE_INT_MAP;

/*************************************************************************************************/


PROCEDURE usp_RPT_IndicesE_MAP_SELEC(p IN VARCHAR2, q IN VARCHAR2, cursorOut OUT t_cursor)
--p =  usuario
--q = oficina

IS
  ret_cursor t_cursor;


BEGIN


  OPEN ret_cursor FOR

SELECT DISTINCT

	ES.Num_RegistroOF,
	(CASE
   WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				        RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
	  THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		    TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
        				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				        AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
	END) AS NumRegistro,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha,

	(CASE
   WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
     THEN CASE
            WHEN OG.CD_Organosup IS NULL
			        THEN DEP.DS_Departamento || '.' || OG.DS_Organo ||
                   NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                   			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
             	ELSE DEP.DS_Departamento || '.' || OG.DS_Organo ||
                    ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                             WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                              AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                   NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                   			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			    END
     ELSE
     		  CASE
            WHEN ES.CD_Org_Origen IS NOT NULL
            	THEN
                CASE WHEN OG.CD_Organosup IS NULL
                	THEN OG.DS_Organo ||
                  		 NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                       		  RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                  ELSE OG.DS_Organo || ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                                WHERE	OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                                                 AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                       NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                       			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				          END
		          ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
              			RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		      END
	   END) AS OrgOrigen,

  ES.Cd_Div_Destino,

	(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN DEP1.DS_Departamento || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo ||
      DECODE (OG1.CD_Oficina, NULL,'', ' - (' || OFI2.DS_Oficina || ')')
			ELSE DEP1.DS_Departamento || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
       (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
         AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
         DECODE (OG1.CD_Oficina, NULL,'', ' - (' || OFI2.DS_Oficina || ')')
			END
	ELSE
		CASE WHEN CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				   THEN ES.CD_Org_Destino || ' ' || OG1.DS_Organo ||
           DECODE (OG1.CD_Oficina, NULL,'', ' - (' || OFI2.DS_Oficina || ')')
				   ELSE ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
              (SELECT DS_Organo FROM AUX_ORGANOS_PROD
					    WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
              AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
              DECODE (OG1.CD_Oficina, NULL,'', ' - (' || OFI2.DS_Oficina || ')')
				   END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		  END
	END) AS OrgDestino,

  	(select rel1.direccion || ' ' || rel1.cp || ' ' || loc1.ds_localidad
    from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
    where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
     and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
     and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as DireccionOrgDestino,

  (select loc1.cd_provincia
    from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
    where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
    and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
    and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as CodigoProvincia,

	ES.Resumen,
	OFI.Direccion || ' ' ||
	OFI.CP || ' ' ||
  (select loc2.DS_Localidad from AUX_LOCALIDADES loc2
  where loc2.CD_Provincia = OFI.CD_PROVINCIA and loc2.CD_Localidad = OFI.Cd_Localidad) as DireccionOficina,

	(select c.DS_Logo
   from Dat_Config c inner join USUARIOS uu on
   uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = p) as Organismo

FROM
	AUX_OFICINAS OFI,
  AUX_OFICINAS OFI2,
  REGISTROES ES,
  AUX_ORGANOS_PROD OG,
  AUX_ORGANOS_PROD OG1,
	AUX_DEPARTAMENTOS DEP,
  AUX_DEPARTAMENTOS DEP1,
  TEMP_LISTADO_REG_ENT TEMP

WHERE
  TEMP.EJERCICIO = ES.EJERCICIO
  AND TEMP.TIPOES = ES.TIPOES
  AND TEMP.NUM_REGISTRO = ES.NUM_REGISTRO
  AND TEMP.CD_USUARIO = p
  AND TEMP.CD_OFICINA = q
  AND ES.CD_OFICINA = OFI.CD_OFICINA
	AND ES.CD_Div_Origen=OG.CD_Division(+)
	AND ES.CD_Org_Origen=OG.CD_Organo(+)
	AND ES.CD_Org_Origen=DEP.CD_Organo(+)
	AND ES.CD_Dep_Origen=DEP.CD_Departamento(+)
	AND ES.CD_Org_Destino=DEP1.CD_Organo(+)
	AND ES.CD_Dep_Destino=DEP1.CD_Departamento(+)
	AND ES.CD_Div_Destino = OG1.CD_Division (+)
	AND ES.CD_Org_Destino = OG1.CD_Organo (+)
  AND OG1.CD_Oficina = OFI2.CD_Oficina (+)


ORDER BY  OrgDestino, Num_RegistroOF;

  cursorOut:=ret_cursor;


END usp_RPT_IndicesE_MAP_SELEC;

/*************************************************************************************************/

PROCEDURE usp_RPT_IndicesE_Destino(P_USUARIO  IN VARCHAR2, -- Uusuario
                                   P_OFICINA        IN VARCHAR2, -- Código de oficina (letra O)
                                   P_ORGANO         IN VARCHAR2, -- Código de órgano (letra A)
                                   P_DEP            IN VARCHAR2, -- Código de departamento (letra B)
                                   P_F_INI          IN DATE, -- Fecha Inicio (Filtro)
                                   P_F_FIN          IN DATE, -- Fecha Final (Filtro)
                                   P_REG_INI        IN INTEGER, -- Num Registro Inicio (Filtro)
                                   P_REG_FIN        IN INTEGER, -- Num Registro Final (Filtro)
                                   P_FILTRO_DIV     IN INTEGER, -- Código de División (Filtro)
                                   P_FILTRO_ORG     IN VARCHAR2, -- Código de Órgano (Filtro)
                                   cursorOut        OUT t_cursor) IS

  v_usuario USUARIOS.CD_USUARIO%TYPE := P_USUARIO;

  v_oficina  REGISTROES.CD_OFICINA%TYPE := P_OFICINA;
  v_organo   REGISTROES.CD_ORG_DESTINO%TYPE := P_ORGANO;
  v_dep      REGISTROES.CD_DEP_DESTINO%TYPE := P_DEP;

  v_f_ini      REGISTROES.FECHA%TYPE := P_F_INI;
  v_f_fin      REGISTROES.FECHA%TYPE := P_F_FIN;
  v_reg_ini    REGISTROES.NUM_REGISTROOF%TYPE := P_REG_INI;
  v_reg_fin    REGISTROES.NUM_REGISTROOF%TYPE := P_REG_FIN;
  v_filtro_div REGISTROES.CD_DIV_DESTINO%TYPE := P_FILTRO_DIV;
  v_filtro_org REGISTROES.CD_ORG_DESTINO%TYPE := P_FILTRO_ORG;

BEGIN

  -- Si el procedimiento recibe la oficina, significa que se ejecuta desde la vista de registros pendientes de E/R
  IF v_oficina is not null THEN

    OPEN cursorOut FOR

      SELECT v_f_ini AS fechai,
             v_f_fin AS fechaf,
             ES.Num_RegistroOF,
             (CASE
               WHEN (SELECT Num_RG
                       FROM REGISTROGENERAL RG
                      WHERE RG.Ejercicio = ES.Ejercicio
                        AND RG.TipoES = ES.TipoES
                        AND RG.CD_Oficina = ES.CD_Oficina
                        AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL THEN
                TO_CHAR(ES.Num_RegistroOF) || '/RG ' ||
                TO_CHAR((SELECT Num_RG
                          FROM REGISTROGENERAL RG
                         WHERE RG.Ejercicio = ES.Ejercicio
                           AND RG.TipoES = ES.TipoES
                           AND RG.CD_Oficina = ES.CD_Oficina
                           AND RG.Num_Registro = ES.Num_RegistroOF))
               ELSE
                TO_CHAR(ES.Num_RegistroOF)
             END) AS NumRegistro,

             OFI.DS_Oficina,
             ES.FechaP AS Fecha,

             US.DS_Usuario,
             (CASE
               WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL THEN
                CASE
               WHEN OG.CD_Organosup IS NULL THEN
                DEP.DS_Departamento || '.' || OG.DS_Organo ||
                NVL((SELECT ' / ' || MAX(Nombre)
                      FROM REL_INTVSRES RINT
                     WHERE RINT.Ejercicio = ES.Ejercicio
                       AND RINT.TipoES = ES.TipoES
                       AND RINT.Num_Registro = ES.Num_Registro
                     GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro),
                    ' ')
               ELSE
                DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
                (SELECT DS_Organo
                   FROM AUX_ORGANOS_PROD
                  WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                    AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
                NVL((SELECT ' / ' || MAX(Nombre)
                      FROM REL_INTVSRES RINT
                     WHERE RINT.Ejercicio = ES.Ejercicio
                       AND RINT.TipoES = ES.TipoES
                       AND RINT.Num_Registro = ES.Num_Registro
                     GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro),
                    ' ')
             END ELSE CASE
                WHEN ES.CD_Org_Origen IS NOT NULL THEN
                 CASE
                WHEN OG.CD_Organosup IS NULL THEN
                 OG.DS_Organo ||
                 NVL((SELECT ' / ' || MAX(Nombre)
                       FROM REL_INTVSRES RINT
                      WHERE RINT.Ejercicio = ES.Ejercicio
                        AND RINT.TipoES = ES.TipoES
                        AND RINT.Num_Registro = ES.Num_Registro
                      GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro),
                     ' ')
                ELSE
                 OG.DS_Organo || ' (' ||
                 (SELECT DS_Organo
                    FROM AUX_ORGANOS_PROD
                   WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                     AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
                 NVL((SELECT ' / ' || MAX(Nombre)
                       FROM REL_INTVSRES RINT
                      WHERE RINT.Ejercicio = ES.Ejercicio
                        AND RINT.TipoES = ES.TipoES
                        AND RINT.Num_Registro = ES.Num_Registro
                      GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro),
                     ' ')
              END ELSE
              (SELECT MAX(Nombre)
                 FROM REL_INTVSRES RINT
                WHERE RINT.Ejercicio = ES.Ejercicio
                  AND RINT.TipoES = ES.TipoES
                  AND RINT.Num_Registro = ES.Num_Registro
                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro) END END) AS OrgOrigen,

             ES.CD_Org_Destino AS CodOrganoD,
             ES.Cd_Div_Destino,

             (CASE
               WHEN CD_Org_Destino IS NOT NULL AND
                    CD_Dep_Destino IS NOT NULL AND
                    (SELECT DISTINCT T.CD_Tramite
                       FROM RegistroESTra T
                      WHERE ES.Ejercicio = T.Ejercicio
                        AND ES.TipoES = T.TipoES
                        AND ES.Num_Registro = T.Num_Registro
                        AND T.CD_tramite = 'RevisarORG') IS NULL THEN
                CASE
               WHEN OG1.CD_Organosup IS NULL THEN
                NVL(DEP1.DS_Departamento, '') || '. ' || ES.CD_Org_Destino || ' ' ||
                OG1.DS_Organo
               ELSE
                NVL(DEP1.DS_Departamento, '') || '. ' || ES.CD_Org_Destino || ' ' ||
                OG1.DS_Organo || ' (' ||
                (SELECT DS_Organo
                   FROM AUX_ORGANOS_PROD
                  WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                    AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
             END ELSE CASE
                WHEN CD_Org_Destino IS NOT NULL THEN
                 CASE
                WHEN OG1.CD_Organosup IS NULL

                 THEN
                 NVL(DEP1.DS_Departamento, '') || '. ' || ES.CD_Org_Destino || ' ' ||
                 OG1.DS_Organo
                ELSE
                 NVL(DEP1.DS_Departamento, '') || '. ' || ES.CD_Org_Destino || ' ' ||
                 OG1.DS_Organo || ' (' ||

                 (SELECT DS_Organo
                    FROM AUX_ORGANOS_PROD
                   WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                     AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
              END ELSE
              (SELECT MAX(Nombre)
                 FROM REL_INTVSRES RINT
                WHERE RINT.Ejercicio = ES.Ejercicio
                  AND RINT.TipoES = ES.TipoES
                  AND RINT.Num_Registro = ES.Num_Registro
                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro) END END) AS OrgDestino,

             (select rel1.direccion || ' ' || rel1.cp || ' ' ||
                     loc1.ds_localidad
                from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
               where rel1.cd_provincia = loc1.cd_provincia
                 and rel1.cd_localidad = loc1.cd_localidad
                 and rel1.cd_provincia = CD_Prov_Destino
                 and rel1.cd_localidad = CD_Loc_Destino
                 and rel1.cd_division = CD_Div_Destino
                 and rel1.cd_organo = CD_Org_Destino) as DireccionOrgDestino,

             (select loc1.cd_provincia
                from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
               where rel1.cd_provincia = loc1.cd_provincia
                 and rel1.cd_localidad = loc1.cd_localidad
                 and rel1.cd_provincia = CD_Prov_Destino
                 and rel1.cd_localidad = CD_Loc_Destino
                 and rel1.cd_division = CD_Div_Destino
                 and rel1.cd_organo = CD_Org_Destino) as CodigoProvincia,
             NVL(CM.CONTENIDO, '') || ' ' || ES.Resumen || '. ' || ES.Obs as Resumen,

             OFI.Direccion || ' ' || OFI.CP || ' ' ||
             (select loc2.DS_Localidad
                from AUX_LOCALIDADES loc2
               where loc2.CD_Provincia = OFI.CD_PROVINCIA
                 and loc2.CD_Localidad = OFI.Cd_Localidad) as DireccionOficina,

             (select c.DS_Logo from Dat_Config c where CD_Organismo = 5) as Organismo,
             /*  CASE WHEN v_ta IS NOT NULL
                  THEN CM.DS_COMENTARIO
                  ELSE ''
               END AS TipoAsunto*/

             (SELECT MAX(TO_CHAR(TO_DATE(TRA.F_EJECUCION), 'DD/MM/YYYY'))
                FROM REGISTROESTRA TRA
               WHERE ES.EJERCICIO = TRA.EJERCICIO
                 AND ES.TIPOES = TRA.TIPOES
                 AND ES.NUM_REGISTRO = TRA.NUM_REGISTRO
                 AND TRA.CD_CAMINO = '2'
                 AND TRA.CD_TRAMITE = 'RecibirUnidad'
                 AND TRA.CD_OFICINA = 'GENMAP'
                 AND TRA.F_EJECUCION IS NOT NULL) AS FECHA_RECEP

        FROM AUX_OFICINAS      OFI,
             REGISTROES        ES,
             AUX_COMENTARIOS   CM,
             AUX_ORGANOS_PROD  OG,
             AUX_ORGANOS_PROD  OG1,
             AUX_OFICINAS      OFI2,
             AUX_DEPARTAMENTOS DEP,
             AUX_DEPARTAMENTOS DEP1,
             USUARIOS          US
       WHERE --ES.Ejercicio = v_ejercicio
        -- AND
         ES.TipoES = 'E'
         AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
         AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
         AND (ES.Num_RegistroOF >= v_reg_ini OR v_reg_ini = 0)
         AND (ES.Num_RegistroOF <= v_reg_fin OR v_reg_fin = 0)
         AND (ES.CD_Div_Destino = v_filtro_div OR v_filtro_div = 0)
         AND (ES.CD_Org_Destino = v_filtro_org OR v_filtro_org IS NULL)
         AND (TO_CHAR(TO_DATE(ES.Fecha), 'YYYY-MM-DD') >=
             TO_CHAR(TO_DATE(v_f_ini), 'YYYY-MM-DD') OR v_f_ini IS NULL)
         AND (TO_CHAR(TO_DATE(ES.Fecha), 'YYYY-MM-DD') <=
             TO_CHAR(TO_DATE(v_f_fin), 'YYYY-MM-DD') OR v_f_fin IS NULL)
         AND OFI.CD_Oficina = ES.CD_Oficina
         AND ES.Unidad_Registral = v_oficina
         AND ES.Unidad_Registral <> ES.CD_Oficina

            -- Bloque para mostrar la select
         AND (ES.CD_Div_Origen = OG.CD_Division(+))
         AND (ES.CD_Org_Origen = OG.CD_Organo(+))
         AND (ES.CD_Org_Origen = DEP.CD_Organo(+))
         AND (ES.CD_Dep_Origen = DEP.CD_Departamento(+))
         AND OG1.CD_Oficina = OFI2.CD_Oficina(+)
         AND ES.CD_Org_Destino = DEP1.CD_Organo(+)
         AND ES.CD_Dep_Destino = DEP1.CD_Departamento(+)
         AND (ES.CD_Div_Destino = OG1.CD_Division)
         AND (ES.CD_Org_Destino = OG1.CD_Organo)
         AND US.CD_USUARIO = v_usuario

       ORDER BY ES.TipoES, ES.CD_Oficina, ES.Num_RegistroOF DESC;

    -----------------------------------

  ELSE
    -- Si el procedimiento recibe el órgano, significa que se ejecuta desde la vista de Consulta Registro por unidad
    -- Si se ejececuta desde la vista de Órgano o Subórgano se contempla con el campo departamento
    IF v_organo IS NOT NULL AND v_dep IS NULL THEN

      OPEN cursorOut FOR

        SELECT v_f_ini AS fechai,
               v_f_fin AS fechaf,
               ES.Num_RegistroOF,
               (CASE
                 WHEN (SELECT Num_RG
                         FROM REGISTROGENERAL RG
                        WHERE RG.Ejercicio = ES.Ejercicio
                          AND RG.TipoES = ES.TipoES
                          AND RG.CD_Oficina = ES.CD_Oficina
                          AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL THEN
                  TO_CHAR(ES.Num_RegistroOF) || '/RG ' ||
                  TO_CHAR((SELECT Num_RG
                            FROM REGISTROGENERAL RG
                           WHERE RG.Ejercicio = ES.Ejercicio
                             AND RG.TipoES = ES.TipoES
                             AND RG.CD_Oficina = ES.CD_Oficina
                             AND RG.Num_Registro = ES.Num_RegistroOF))
                 ELSE
                  TO_CHAR(ES.Num_RegistroOF)
               END) AS NumRegistro,

               OFI.DS_Oficina,
               ES.FechaP AS Fecha,

               US.DS_Usuario,
               (CASE
                 WHEN ES.CD_Org_Origen IS NOT NULL AND
                      CD_Dep_Origen IS NOT NULL THEN
                  CASE
                 WHEN OG.CD_Organosup IS NULL THEN
                  DEP.DS_Departamento || '.' || OG.DS_Organo ||
                  NVL((SELECT ' / ' || MAX(Nombre)
                        FROM REL_INTVSRES RINT
                       WHERE RINT.Ejercicio = ES.Ejercicio
                         AND RINT.TipoES = ES.TipoES
                         AND RINT.Num_Registro = ES.Num_Registro
                       GROUP BY RINT.Ejercicio,
                                RINT.TipoES,
                                RINT.Num_Registro),
                      ' ')
                 ELSE
                  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
                  (SELECT DS_Organo
                     FROM AUX_ORGANOS_PROD
                    WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                      AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
                  NVL((SELECT ' / ' || MAX(Nombre)
                        FROM REL_INTVSRES RINT
                       WHERE RINT.Ejercicio = ES.Ejercicio
                         AND RINT.TipoES = ES.TipoES
                         AND RINT.Num_Registro = ES.Num_Registro
                       GROUP BY RINT.Ejercicio,
                                RINT.TipoES,
                                RINT.Num_Registro),
                      ' ')
               END ELSE CASE
                  WHEN ES.CD_Org_Origen IS NOT NULL THEN
                   CASE
                  WHEN OG.CD_Organosup IS NULL THEN
                   OG.DS_Organo ||
                   NVL((SELECT ' / ' || MAX(Nombre)
                         FROM REL_INTVSRES RINT
                        WHERE RINT.Ejercicio = ES.Ejercicio
                          AND RINT.TipoES = ES.TipoES
                          AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio,
                                 RINT.TipoES,
                                 RINT.Num_Registro),
                       ' ')
                  ELSE
                   OG.DS_Organo || ' (' ||
                   (SELECT DS_Organo
                      FROM AUX_ORGANOS_PROD
                     WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                       AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
                   NVL((SELECT ' / ' || MAX(Nombre)
                         FROM REL_INTVSRES RINT
                        WHERE RINT.Ejercicio = ES.Ejercicio
                          AND RINT.TipoES = ES.TipoES
                          AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio,
                                 RINT.TipoES,
                                 RINT.Num_Registro),
                       ' ')
                END ELSE
                (SELECT MAX(Nombre)
                   FROM REL_INTVSRES RINT
                  WHERE RINT.Ejercicio = ES.Ejercicio
                    AND RINT.TipoES = ES.TipoES
                    AND RINT.Num_Registro = ES.Num_Registro
                  GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro) END END) AS OrgOrigen,

               ES.CD_Org_Destino AS CodOrganoD,
               ES.Cd_Div_Destino,

               (CASE
                 WHEN CD_Org_Destino IS NOT NULL AND
                      CD_Dep_Destino IS NOT NULL AND
                      (SELECT DISTINCT T.CD_Tramite
                         FROM RegistroESTra T
                        WHERE ES.Ejercicio = T.Ejercicio
                          AND ES.TipoES = T.TipoES
                          AND ES.Num_Registro = T.Num_Registro
                          AND T.CD_tramite = 'RevisarORG') IS NULL THEN
                  CASE
                 WHEN OG1.CD_Organosup IS NULL THEN
                  NVL(DEP1.DS_Departamento, '') || '. ' || ES.CD_Org_Destino || ' ' ||
                  OG1.DS_Organo
                 ELSE
                  NVL(DEP1.DS_Departamento, '') || '. ' || ES.CD_Org_Destino || ' ' ||
                  OG1.DS_Organo || ' (' ||
                  (SELECT DS_Organo
                     FROM AUX_ORGANOS_PROD
                    WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                      AND OG1.CD_DIVISIONSUP =
                          AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
               END ELSE CASE
                  WHEN CD_Org_Destino IS NOT NULL THEN
                   CASE
                  WHEN OG1.CD_Organosup IS NULL

                   THEN
                   NVL(DEP1.DS_Departamento, '') || '. ' || ES.CD_Org_Destino || ' ' ||
                   OG1.DS_Organo
                  ELSE
                   NVL(DEP1.DS_Departamento, '') || '. ' || ES.CD_Org_Destino || ' ' ||
                   OG1.DS_Organo || ' (' ||

                   (SELECT DS_Organo
                      FROM AUX_ORGANOS_PROD
                     WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                       AND OG1.CD_DIVISIONSUP =
                           AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
                END ELSE
                (SELECT MAX(Nombre)
                   FROM REL_INTVSRES RINT
                  WHERE RINT.Ejercicio = ES.Ejercicio
                    AND RINT.TipoES = ES.TipoES
                    AND RINT.Num_Registro = ES.Num_Registro
                  GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro) END END) AS OrgDestino,

               (select rel1.direccion || ' ' || rel1.cp || ' ' ||
                       loc1.ds_localidad
                  from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
                 where rel1.cd_provincia = loc1.cd_provincia
                   and rel1.cd_localidad = loc1.cd_localidad
                   and rel1.cd_provincia = CD_Prov_Destino
                   and rel1.cd_localidad = CD_Loc_Destino
                   and rel1.cd_division = CD_Div_Destino
                   and rel1.cd_organo = CD_Org_Destino) as DireccionOrgDestino,

               (select loc1.cd_provincia
                  from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
                 where rel1.cd_provincia = loc1.cd_provincia
                   and rel1.cd_localidad = loc1.cd_localidad
                   and rel1.cd_provincia = CD_Prov_Destino
                   and rel1.cd_localidad = CD_Loc_Destino
                   and rel1.cd_division = CD_Div_Destino
                   and rel1.cd_organo = CD_Org_Destino) as CodigoProvincia,
               NVL(CM.CONTENIDO, '') || ' ' || ES.Resumen || '. ' || ES.Obs as Resumen,

               OFI.Direccion || ' ' || OFI.CP || ' ' ||
               (select loc2.DS_Localidad
                  from AUX_LOCALIDADES loc2
                 where loc2.CD_Provincia = OFI.CD_PROVINCIA
                   and loc2.CD_Localidad = OFI.Cd_Localidad) as DireccionOficina,

               (select c.DS_Logo from Dat_Config c where CD_Organismo = 5) as Organismo,
               /*  CASE WHEN v_ta IS NOT NULL
                    THEN CM.DS_COMENTARIO
                    ELSE ''
                 END AS TipoAsunto*/

               (SELECT MAX(TO_CHAR(TO_DATE(TRA.F_EJECUCION), 'DD/MM/YYYY'))
                  FROM REGISTROESTRA TRA
                 WHERE ES.EJERCICIO = TRA.EJERCICIO
                   AND ES.TIPOES = TRA.TIPOES
                   AND ES.NUM_REGISTRO = TRA.NUM_REGISTRO
                   AND TRA.CD_CAMINO = '2'
                   AND TRA.CD_TRAMITE = 'RecibirRegistro'
                   AND TRA.F_EJECUCION IS NOT NULL) AS FECHA_RECEP

          FROM vs_RegistroES2    ES,
               AUX_OFICINAS      OFI,
               REGISTROESTRA     it,
               AUX_COMENTARIOS   CM,
               AUX_ORGANOS_PROD  OG,
               AUX_ORGANOS_PROD  OG1,
               AUX_OFICINAS      OFI2,
               AUX_DEPARTAMENTOS DEP,
               AUX_DEPARTAMENTOS DEP1,
               USUARIOS          US
         WHERE --it.cd_division = v_division
           it.Cd_Organo = v_organo
           AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
           AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
           --AND ES.CD_Dep_Destino IS NULL-- SÓLO PARA ÓRGANOS
           AND ES.TipoES = 'E'
           AND OFI.CD_Oficina = ES.CD_Oficina
           --AND ES.Ejercicio = V_EJERCICIO
           AND (ES.Fecha >= V_F_INI OR V_F_INI IS NULL)
           AND (ES.Fecha <= V_F_FIN OR V_F_FIN IS NULL)
           AND (ES.Num_RegistroOF >= v_reg_ini OR v_reg_ini = 0)
           AND (ES.Num_RegistroOF <= v_reg_fin OR v_reg_fin = 0)
           AND ES.Ejercicio = it.Ejercicio
           AND ES.TipoES = it.TipoES
           AND ES.Num_Registro = it.Num_Registro
           AND it.CD_Camino = 2
           AND it.CD_Tramite = 'RecibirRegistro'
           AND it.devuelto is null
           AND it.F_Ejecucion IS NOT NULL
              --AND ((es.CD_Dep_Destino = V_DEP_DESTINO) OR V_ES_DEP IS NULL)--> SÓLO PARA SUBORGANO
           AND it.CD_Organo = ES.CD_Org_Destino
              -- AND (it.CD_Departamento = ES.CD_Dep_Destino OR V_ES_DEP IS NULL) --> SÓLO PARA SUBORGANO

              -- Bloque para mostrar la select
           AND (ES.CD_Div_Origen = OG.CD_Division(+))
           AND (ES.CD_Org_Origen = OG.CD_Organo(+))
           AND (ES.CD_Org_Origen = DEP.CD_Organo(+))
           AND (ES.CD_Dep_Origen = DEP.CD_Departamento(+))
           AND OG1.CD_Oficina = OFI2.CD_Oficina(+)
           AND ES.CD_Org_Destino = DEP1.CD_Organo(+)
           AND ES.CD_Dep_Destino = DEP1.CD_Departamento(+)
           AND (ES.CD_Div_Destino = OG1.CD_Division)
           AND (ES.CD_Org_Destino = OG1.CD_Organo)
           AND US.CD_USUARIO = v_usuario
           AND IT.CD_DEPARTAMENTO IS NULL

         --ORDER BY ES.CD_Oficina, ES.Num_RegistroOF DESC;
         ORDER BY ES.CD_Oficina, CD_DIV_DESTINO, ORGDESTINO, ES.Num_RegistroOF DESC;

    ELSE
      -- Ejecución desde la vista de Consulta Registro por unidad para subórganos

      OPEN cursorOut FOR

        SELECT v_f_ini AS fechai,
               v_f_fin AS fechaf,
               ES.Num_RegistroOF,
               (CASE
                 WHEN (SELECT Num_RG
                         FROM REGISTROGENERAL RG
                        WHERE RG.Ejercicio = ES.Ejercicio
                          AND RG.TipoES = ES.TipoES
                          AND RG.CD_Oficina = ES.CD_Oficina
                          AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL THEN
                  TO_CHAR(ES.Num_RegistroOF) || '/RG ' ||
                  TO_CHAR((SELECT Num_RG
                            FROM REGISTROGENERAL RG
                           WHERE RG.Ejercicio = ES.Ejercicio
                             AND RG.TipoES = ES.TipoES
                             AND RG.CD_Oficina = ES.CD_Oficina
                             AND RG.Num_Registro = ES.Num_RegistroOF))
                 ELSE
                  TO_CHAR(ES.Num_RegistroOF)
               END) AS NumRegistro,

               OFI.DS_Oficina,
               ES.FechaP AS Fecha,

               US.DS_Usuario,
               (CASE
                 WHEN ES.CD_Org_Origen IS NOT NULL AND
                      CD_Dep_Origen IS NOT NULL THEN
                  CASE
                 WHEN OG.CD_Organosup IS NULL THEN
                  DEP.DS_Departamento || '.' || OG.DS_Organo ||
                  NVL((SELECT ' / ' || MAX(Nombre)
                        FROM REL_INTVSRES RINT
                       WHERE RINT.Ejercicio = ES.Ejercicio
                         AND RINT.TipoES = ES.TipoES
                         AND RINT.Num_Registro = ES.Num_Registro
                       GROUP BY RINT.Ejercicio,
                                RINT.TipoES,
                                RINT.Num_Registro),
                      ' ')
                 ELSE
                  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
                  (SELECT DS_Organo
                     FROM AUX_ORGANOS_PROD
                    WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                      AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
                  NVL((SELECT ' / ' || MAX(Nombre)
                        FROM REL_INTVSRES RINT
                       WHERE RINT.Ejercicio = ES.Ejercicio
                         AND RINT.TipoES = ES.TipoES
                         AND RINT.Num_Registro = ES.Num_Registro
                       GROUP BY RINT.Ejercicio,
                                RINT.TipoES,
                                RINT.Num_Registro),
                      ' ')
               END ELSE CASE
                  WHEN ES.CD_Org_Origen IS NOT NULL THEN
                   CASE
                  WHEN OG.CD_Organosup IS NULL THEN
                   OG.DS_Organo ||
                   NVL((SELECT ' / ' || MAX(Nombre)
                         FROM REL_INTVSRES RINT
                        WHERE RINT.Ejercicio = ES.Ejercicio
                          AND RINT.TipoES = ES.TipoES
                          AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio,
                                 RINT.TipoES,
                                 RINT.Num_Registro),
                       ' ')
                  ELSE
                   OG.DS_Organo || ' (' ||
                   (SELECT DS_Organo
                      FROM AUX_ORGANOS_PROD
                     WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                       AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
                   NVL((SELECT ' / ' || MAX(Nombre)
                         FROM REL_INTVSRES RINT
                        WHERE RINT.Ejercicio = ES.Ejercicio
                          AND RINT.TipoES = ES.TipoES
                          AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio,
                                 RINT.TipoES,
                                 RINT.Num_Registro),
                       ' ')
                END ELSE
                (SELECT MAX(Nombre)
                   FROM REL_INTVSRES RINT
                  WHERE RINT.Ejercicio = ES.Ejercicio
                    AND RINT.TipoES = ES.TipoES
                    AND RINT.Num_Registro = ES.Num_Registro
                  GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro) END END) AS OrgOrigen,

               ES.CD_Org_Destino AS CodOrganoD,
               ES.Cd_Div_Destino,

               (CASE
                 WHEN CD_Org_Destino IS NOT NULL AND
                      CD_Dep_Destino IS NOT NULL AND
                      (SELECT DISTINCT T.CD_Tramite
                         FROM RegistroESTra T
                        WHERE ES.Ejercicio = T.Ejercicio
                          AND ES.TipoES = T.TipoES
                          AND ES.Num_Registro = T.Num_Registro
                          AND T.CD_tramite = 'RevisarORG') IS NULL THEN
                  CASE
                 WHEN OG1.CD_Organosup IS NULL THEN
                  NVL(DEP1.DS_Departamento, '') || '. ' || ES.CD_Org_Destino || ' ' ||
                  OG1.DS_Organo
                 ELSE
                  NVL(DEP1.DS_Departamento, '') || '. ' || ES.CD_Org_Destino || ' ' ||
                  OG1.DS_Organo || ' (' ||
                  (SELECT DS_Organo
                     FROM AUX_ORGANOS_PROD
                    WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                      AND OG1.CD_DIVISIONSUP =
                          AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
               END ELSE CASE
                  WHEN CD_Org_Destino IS NOT NULL THEN
                   CASE
                  WHEN OG1.CD_Organosup IS NULL

                   THEN
                   NVL(DEP1.DS_Departamento, '') || '. ' || ES.CD_Org_Destino || ' ' ||
                   OG1.DS_Organo
                  ELSE
                   NVL(DEP1.DS_Departamento, '') || '. ' || ES.CD_Org_Destino || ' ' ||
                   OG1.DS_Organo || ' (' ||

                   (SELECT DS_Organo
                      FROM AUX_ORGANOS_PROD
                     WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                       AND OG1.CD_DIVISIONSUP =
                           AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
                END ELSE
                (SELECT MAX(Nombre)
                   FROM REL_INTVSRES RINT
                  WHERE RINT.Ejercicio = ES.Ejercicio
                    AND RINT.TipoES = ES.TipoES
                    AND RINT.Num_Registro = ES.Num_Registro
                  GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro) END END) AS OrgDestino,

               (select rel1.direccion || ' ' || rel1.cp || ' ' ||
                       loc1.ds_localidad
                  from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
                 where rel1.cd_provincia = loc1.cd_provincia
                   and rel1.cd_localidad = loc1.cd_localidad
                   and rel1.cd_provincia = CD_Prov_Destino
                   and rel1.cd_localidad = CD_Loc_Destino
                   and rel1.cd_division = CD_Div_Destino
                   and rel1.cd_organo = CD_Org_Destino) as DireccionOrgDestino,

               (select loc1.cd_provincia
                  from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
                 where rel1.cd_provincia = loc1.cd_provincia
                   and rel1.cd_localidad = loc1.cd_localidad
                   and rel1.cd_provincia = CD_Prov_Destino
                   and rel1.cd_localidad = CD_Loc_Destino
                   and rel1.cd_division = CD_Div_Destino
                   and rel1.cd_organo = CD_Org_Destino) as CodigoProvincia,
               NVL(CM.CONTENIDO, '') || ' ' || ES.Resumen || '. ' || ES.Obs as Resumen,

               OFI.Direccion || ' ' || OFI.CP || ' ' ||
               (select loc2.DS_Localidad
                  from AUX_LOCALIDADES loc2
                 where loc2.CD_Provincia = OFI.CD_PROVINCIA
                   and loc2.CD_Localidad = OFI.Cd_Localidad) as DireccionOficina,

               (select c.DS_Logo from Dat_Config c where CD_Organismo = 5) as Organismo,
               /*  CASE WHEN v_ta IS NOT NULL
                    THEN CM.DS_COMENTARIO
                    ELSE ''
                 END AS TipoAsunto*/

               (SELECT MAX(TO_CHAR(TO_DATE(TRA.F_EJECUCION), 'DD/MM/YYYY'))
                  FROM REGISTROESTRA TRA
                 WHERE ES.EJERCICIO = TRA.EJERCICIO
                   AND ES.TIPOES = TRA.TIPOES
                   AND ES.NUM_REGISTRO = TRA.NUM_REGISTRO
                   AND TRA.CD_CAMINO = '2'
                   AND TRA.CD_TRAMITE = 'RecibirRegistro'
                   AND TRA.F_EJECUCION IS NOT NULL) AS FECHA_RECEP

          FROM vs_RegistroES2    ES,
               AUX_OFICINAS      OFI,
               REGISTROESTRA     it,
               AUX_COMENTARIOS   CM,
               AUX_ORGANOS_PROD  OG,
               AUX_ORGANOS_PROD  OG1,
               AUX_OFICINAS      OFI2,
               AUX_DEPARTAMENTOS DEP,
               AUX_DEPARTAMENTOS DEP1,
               USUARIOS          US
         WHERE --it.cd_division = v_division
           it.Cd_Organo = v_organo
           AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
           AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
              --AND it.CD_Departamento IS NULL-- SÓLO PARA ÓRGANOS
           AND ES.TipoES = 'E'
           AND OFI.CD_Oficina = ES.CD_Oficina
           --AND ES.Ejercicio = V_EJERCICIO
           AND (ES.Fecha >= V_F_INI OR V_F_INI IS NULL)
           AND (ES.Fecha <= V_F_FIN OR V_F_FIN IS NULL)
           AND (ES.Num_RegistroOF >= v_reg_ini OR v_reg_ini = 0)
           AND (ES.Num_RegistroOF <= v_reg_fin OR v_reg_fin = 0)
           AND ES.Ejercicio = it.Ejercicio
           AND ES.TipoES = it.TipoES
           AND ES.Num_Registro = it.Num_Registro
           AND it.CD_Camino = 2
           AND it.CD_Tramite = 'RecibirRegistro'
           AND it.devuelto is null
           AND it.F_Ejecucion IS NOT NULL
           AND ((es.CD_Dep_Destino = v_dep) OR v_dep IS NULL) --> SÓLO PARA SUBORGANO
           AND it.CD_Organo = ES.CD_Org_Destino
           AND (it.CD_Departamento = ES.CD_Dep_Destino OR v_dep IS NULL) --> SÓLO PARA SUBORGANO

              -- Bloque para mostrar la select
           AND (ES.CD_Div_Origen = OG.CD_Division(+))
           AND (ES.CD_Org_Origen = OG.CD_Organo(+))
           AND (ES.CD_Org_Origen = DEP.CD_Organo(+))
           AND (ES.CD_Dep_Origen = DEP.CD_Departamento(+))
           AND OG1.CD_Oficina = OFI2.CD_Oficina(+)
           AND ES.CD_Org_Destino = DEP1.CD_Organo(+)
           AND ES.CD_Dep_Destino = DEP1.CD_Departamento(+)
           AND (ES.CD_Div_Destino = OG1.CD_Division)
           AND (ES.CD_Org_Destino = OG1.CD_Organo)
           AND US.CD_USUARIO = v_usuario

         --ORDER BY ES.CD_Oficina, ES.Num_RegistroOF DESC;
         ORDER BY ES.CD_Oficina, CD_DIV_DESTINO, ORGDESTINO, ES.Num_RegistroOF DESC;

    END IF;

  END IF;

END usp_RPT_IndicesE_Destino;


/*************************************************************************************************/
PROCEDURE usp_RPT_IndicesS(p IN VARCHAR2, --codigo de la oficina
q IN INTEGER, --division
r IN INTEGER, --organodestino
s IN INTEGER, --registro inicial
t IN INTEGER --registro final
, cursorOut OUT t_cursor) IS

  ret_cursor t_cursor;
  --ss INTEGER;
  --tt INTEGER;

fv  DATE := TO_DATE(q,'YYYY-MM-DD');
ff  DATE := TO_DATE(r,'YYYY-MM-DD');

/*
if @s='' select @s =null
if @t='' select @t =null
declare @fv as datetime
declare @ff as datetime
SET dateformat ymd
select @fv=dateadd(day,@q,'1899-12-30')
select @ff=dateadd(day,@r,'1899-12-30')
*/

BEGIN
  OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
	/*
		decode(numeroreg(ES.Ejercicio,ES.TipoES,ES.CD_Oficina,ES.Num_RegistroOF), ' ',
	       ES.Num_RegistroOF ,
	       ES.Num_RegistroOF  || '/RG ' ||
	       numeroreg(ES.Ejercicio,ES.TipoES,ES.CD_Oficina,ES.Num_RegistroOF))AS NumRegistro,
	*/
	(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF )
		END) AS NumRegistro,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha ,
		DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
   (CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN CD_Organo_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo

				ELSE  OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Organo_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG1.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Organo_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG1.DS_Organo || ' (' || (SELECT AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG1.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
		DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	ES.Num_Transporte,
	ES.Resumen,
	ES.CD_Organo_Destino
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS OG,  AUX_ORGANOS OG1,AUX_TRANSPORTES TR, UsuariosV1 US,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Division_Origen = OG.CD_Division)
	AND (ES.CD_Organo_Origen = OG.CD_Organo)
	AND (ES.CD_Organo_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_Organo_Destino=DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+))
	AND (ES.CD_Division_Destino=OG1.CD_Division(+))
	AND (ES.CD_Organo_Destino=OG1.CD_Organo(+))
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'S'
	AND ES.Fecha  BETWEEN fv AND ff
	AND (q IS NOT NULL OR r IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	--AND (ES.Num_RegistroOF >= s OR s is Null)
	--AND (ES.Num_RegistroOF <= t OR t is Null)
	AND ES.Estado IS NULL

ORDER BY Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_IndicesS;



/*************************************************************************************************/
procedure usp_RPT_IndicesS_fechas(p         IN VARCHAR2,
                                  kk        IN VARCHAR2,
                                  cc        IN VARCHAR2,
                                  s         IN INTEGER,
                                  t         IN INTEGER,
                                  cursorOut OUT t_cursor) IS

  ret_cursor t_cursor;
  fv         DATE := TO_DATE(kk, 'DD/MM/YYYY');
  ff         DATE := TO_DATE(cc, 'DD/MM/YYYY');

BEGIN

  OPEN ret_cursor FOR

    SELECT DISTINCT fv AS fechai,
                    ff AS fechaf,
                    ES.Num_RegistroOF,
                    (CASE
                      WHEN (SELECT Num_RG
                              FROM REGISTROGENERAL RG
                             WHERE RG.Ejercicio = ES.Ejercicio
                               AND RG.TipoES = ES.TipoES
                               AND RG.CD_Oficina = ES.CD_Oficina
                               AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL THEN
                       --TO_CHAR(ES.Num_RegistroOF) || '/RG ' ||
                       TO_CHAR(ES.Num_RegistroOF) || NVL2(OFI.SIGLAOF,' (' || OFI.SIGLAOF || ') ','') || '/RG ' ||
                       TO_CHAR((SELECT Num_RG
                                 FROM REGISTROGENERAL RG
                                WHERE RG.Ejercicio = ES.Ejercicio
                                  AND RG.TipoES = ES.TipoES
                                  AND RG.CD_Oficina = ES.CD_Oficina
                                  AND RG.Num_Registro = ES.Num_RegistroOF))
                      ELSE
                       --TO_CHAR(ES.Num_RegistroOF)
                       TO_CHAR(ES.Num_RegistroOF) || NVL2(OFI.SIGLAOF,' (' || OFI.SIGLAOF || ') ','')
                    END) AS NumRegistro,
                    ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
                    ES.FECHA_REGIS_ORIGINAL AS Fecharem,
                    OFI.DS_Oficina,
                    ES.FechaP AS Fecha,
                    DECODE(ES.TipoES, 'E', 'Entrada', 'S', 'Salida', '') AS TipoES,
                    US.DS_Usuario,
                    (CASE
                      WHEN CD_Organo_Origen IS NOT NULL AND
                           CD_Dep_Origen IS NOT NULL THEN
                       CASE
                      WHEN OG.CD_Organosup IS NULL THEN
                       DEP.DS_Departamento || '.' || OG.DS_Organo
                      ELSE
                       DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
                       (SELECT AUX_OrganosV1.DS_Organo
                          FROM AUX_OrganosV1
                         WHERE OG.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')'
                    END ELSE CASE
                       WHEN CD_Organo_Origen IS NOT NULL THEN
                        CASE
                       WHEN OG.CD_Organosup IS NULL THEN
                        OG.DS_Organo

                       ELSE
                        OG.DS_Organo || ' (' ||
                        (SELECT AUX_OrganosV1.DS_Organo
                           FROM AUX_OrganosV1
                          WHERE OG.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')'
                     END ELSE (SELECT MAX(Nombre)
                                 FROM REL_INTVSRES RINT
                                WHERE RINT.Ejercicio = ES.Ejercicio
                                  AND RINT.TipoES = ES.TipoES
                                  AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio,
                                         RINT.TipoES,
                                         RINT.Num_Registro) END END) AS OrgOrigen,
                    CASE
                      WHEN (CD_Organo_Origen IS NULL AND
                           CD_Dep_Origen IS NULL) OR
                           (CD_Organo_Origen IS NOT NULL) THEN
                       (SELECT MAX(LOCALIDAD)
                          FROM REL_INTVSRES RINT
                         WHERE RINT.Ejercicio = ES.Ejercicio
                           AND RINT.TipoES = ES.TipoES
                           AND RINT.Num_Registro = ES.Num_Registro
                         GROUP BY RINT.Ejercicio,
                                  RINT.TipoES,
                                  RINT.Num_Registro)
                    END AS LOCALIDAD,
                    CASE
                      WHEN (CD_Organo_Origen IS NULL AND
                           CD_Dep_Origen IS NULL) OR
                           (CD_Organo_Origen IS NOT NULL) THEN
                       (SELECT MAX(PAIS)
                          FROM REL_INTVSRES RINT
                         WHERE RINT.Ejercicio = ES.Ejercicio
                           AND RINT.TipoES = ES.TipoES
                           AND RINT.Num_Registro = ES.Num_Registro
                         GROUP BY RINT.Ejercicio,
                                  RINT.TipoES,
                                  RINT.Num_Registro)
                    END AS PAIS,
                    CASE
                      WHEN (CD_Organo_Origen IS NULL AND
                           CD_Dep_Origen IS NULL) OR
                           (CD_Organo_Origen IS NOT NULL) THEN
                       (SELECT DS_PROVINCIA
                          FROM AUX_PROVINCIAS
                         WHERE CD_PROVINCIA =
                               (SELECT MAX(PROVINCIA)
                                  FROM REL_INTVSRES RINT
                                 WHERE RINT.Ejercicio = ES.Ejercicio
                                   AND RINT.TipoES = ES.TipoES
                                   AND RINT.Num_Registro = ES.Num_Registro
                                 GROUP BY RINT.Ejercicio,
                                          RINT.TipoES,
                                          RINT.Num_Registro))
                    END AS PROVINCIA,
                    (CASE
                      WHEN CD_Organo_Destino IS NOT NULL AND
                           CD_Dep_Destino IS NOT NULL THEN
                       CASE
                      WHEN OG1.CD_Organosup IS NULL THEN
                       DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
                       NVL((SELECT ' / ' || MAX(Nombre)
                             FROM REL_INTVSRES RINT
                            WHERE RINT.Ejercicio = ES.Ejercicio
                              AND RINT.TipoES = ES.TipoES
                              AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio,
                                     RINT.TipoES,
                                     RINT.Num_Registro),
                           ' ')
                      ELSE
                       DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
                       (SELECT AUX_OrganosV1.DS_Organo
                          FROM AUX_OrganosV1
                         WHERE OG1.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')' ||
                       NVL((SELECT ' / ' || MAX(Nombre)
                             FROM REL_INTVSRES RINT
                            WHERE RINT.Ejercicio = ES.Ejercicio
                              AND RINT.TipoES = ES.TipoES
                              AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio,
                                     RINT.TipoES,
                                     RINT.Num_Registro),
                           ' ')
                    END ELSE CASE
                       WHEN CD_Organo_Destino IS NOT NULL THEN
                        CASE
                       WHEN OG1.CD_Organosup IS NULL THEN
                        OG1.DS_Organo ||
                        NVL((SELECT ' / ' || MAX(Nombre)
                              FROM REL_INTVSRES RINT
                             WHERE RINT.Ejercicio = ES.Ejercicio
                               AND RINT.TipoES = ES.TipoES
                               AND RINT.Num_Registro = ES.Num_Registro
                             GROUP BY RINT.Ejercicio,
                                      RINT.TipoES,
                                      RINT.Num_Registro),
                            ' ')
                       ELSE
                        OG1.DS_Organo || ' (' ||
                        (SELECT AUX_OrganosV1.DS_Organo
                           FROM AUX_OrganosV1
                          WHERE OG1.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')' ||
                        NVL((SELECT ' / ' || MAX(Nombre)
                              FROM REL_INTVSRES RINT
                             WHERE RINT.Ejercicio = ES.Ejercicio
                               AND RINT.TipoES = ES.TipoES
                               AND RINT.Num_Registro = ES.Num_Registro
                             GROUP BY RINT.Ejercicio,
                                      RINT.TipoES,
                                      RINT.Num_Registro),
                            ' ')
                     END ELSE (SELECT MAX(Nombre)
                                 FROM REL_INTVSRES RINT
                                WHERE RINT.Ejercicio = ES.Ejercicio
                                  AND RINT.TipoES = ES.TipoES
                                  AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio,
                                         RINT.TipoES,
                                         RINT.Num_Registro) END END) AS OrgDestino,
                    ' /' || ES.CD_Regis_Original AS CD_Regis_Original,
                    DECODE(ES.Tipo_Regis_Original,
                           'E',
                           ' / Entrada',
                           'S',
                           ' / Salida',
                           '') AS Tipo_Regis_original,

                    ES.Num_Regis_Original,
                    ES.Fecha_Regis_Original,
                    ES.Tipo_Transporte,
                    TR.DS_Transporte,
                    OFI.Direccion,
                    ES.Num_Transporte,
                    ES.Resumen,
                    ES.CD_Organo_Destino
      FROM AUX_OFICINAS      OFI,
           REGISTROES        ES,
           AUX_ORGANOS       OG,
           AUX_ORGANOS       OG1,
           AUX_TRANSPORTES   TR,
           UsuariosV1        US,
           AUX_DEPARTAMENTOS DEP,
           AUX_DEPARTAMENTOS DEP1
     WHERE ES.CD_Oficina = OFI.CD_Oficina
       AND (ES.CD_Division_Origen = OG.CD_Division)
       AND (ES.CD_Organo_Origen = OG.CD_Organo)
       AND (ES.CD_Organo_Origen = DEP.CD_Organo(+))
       AND (ES.CD_Dep_Origen = DEP.CD_Departamento(+))
       AND (ES.CD_Organo_Destino = DEP1.CD_Organo(+))
       AND (ES.CD_Dep_Destino = DEP1.CD_Departamento(+))
       AND (ES.CD_Division_Destino = OG1.CD_Division(+))
       AND (ES.CD_Organo_Destino = OG1.CD_Organo(+))
       AND (ES.Tipo_Transporte = TR.CD_Transporte(+))
       AND ES.Usuario = US.DNI
       AND OFI.CD_Oficina = p
       AND ES.TipoES = 'S'
       AND (ES.Fecha BETWEEN fv AND ff)
       AND (fv IS NOT NULL OR ff IS NOT NULL)
       AND (ES.Num_RegistroOF >= s OR s = 0)
       AND (ES.Num_RegistroOF <= t OR t = 0)
       AND ES.Estado IS NULL

     ORDER BY OrgOrigen, Num_RegistroOF;

  cursorOut := ret_cursor;
END usp_RPT_IndicesS_fechas;

/*************************************************************************************************/

procedure usp_RPT_IndicesS_fechas_2000(p         IN VARCHAR2,
                                  kk        IN VARCHAR2,
                                  cc        IN VARCHAR2,
                                  s         IN INTEGER,
                                  t         IN INTEGER,
                                  P_Org_Origen IN VARCHAR2,
                                  cursorOut OUT t_cursor) IS

  ret_cursor t_cursor;
  fv         DATE := TO_DATE(kk, 'DD/MM/YYYY');
  ff         DATE := TO_DATE(cc, 'DD/MM/YYYY');

BEGIN

  OPEN ret_cursor FOR

    SELECT DISTINCT fv AS fechai,
                    ff AS fechaf,
                    ES.Num_RegistroOF,
                    (CASE
                      WHEN (SELECT Num_RG
                              FROM REGISTROGENERAL RG
                             WHERE RG.Ejercicio = ES.Ejercicio
                               AND RG.TipoES = ES.TipoES
                               AND RG.CD_Oficina = ES.CD_Oficina
                               AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL THEN
                       --TO_CHAR(ES.Num_RegistroOF) || '/RG ' ||
                       TO_CHAR(ES.Num_RegistroOF) || NVL2(OFI.SIGLAOF,' (' || OFI.SIGLAOF || ') ','') || '/RG ' ||
                       TO_CHAR((SELECT Num_RG
                                 FROM REGISTROGENERAL RG
                                WHERE RG.Ejercicio = ES.Ejercicio
                                  AND RG.TipoES = ES.TipoES
                                  AND RG.CD_Oficina = ES.CD_Oficina
                                  AND RG.Num_Registro = ES.Num_RegistroOF))
                      ELSE
                       --TO_CHAR(ES.Num_RegistroOF)
                       TO_CHAR(ES.Num_RegistroOF) || NVL2(OFI.SIGLAOF,' (' || OFI.SIGLAOF || ') ','')
                    END) AS NumRegistro,
                    ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
                    ES.FECHA_REGIS_ORIGINAL AS Fecharem,
                    OFI.DS_Oficina,
                    ES.FechaP AS Fecha,
                    DECODE(ES.TipoES, 'E', 'Entrada', 'S', 'Salida', '') AS TipoES,
                    US.DS_Usuario,
                    (CASE
                      WHEN CD_Organo_Origen IS NOT NULL AND
                           CD_Dep_Origen IS NOT NULL THEN
                       CASE
                      WHEN OG.CD_Organosup IS NULL THEN
                       DEP.DS_Departamento || '.' || OG.DS_Organo
                      ELSE
                       DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
                       (SELECT AUX_OrganosV1.DS_Organo
                          FROM AUX_OrganosV1
                         WHERE OG.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')'
                    END ELSE CASE
                       WHEN CD_Organo_Origen IS NOT NULL THEN
                        CASE
                       WHEN OG.CD_Organosup IS NULL THEN
                        OG.DS_Organo

                       ELSE
                        OG.DS_Organo || ' (' ||
                        (SELECT AUX_OrganosV1.DS_Organo
                           FROM AUX_OrganosV1
                          WHERE OG.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')'
                     END ELSE (SELECT MAX(Nombre)
                                 FROM REL_INTVSRES RINT
                                WHERE RINT.Ejercicio = ES.Ejercicio
                                  AND RINT.TipoES = ES.TipoES
                                  AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio,
                                         RINT.TipoES,
                                         RINT.Num_Registro) END END) AS OrgOrigen,
                    CASE
                      WHEN (CD_Organo_Origen IS NULL AND
                           CD_Dep_Origen IS NULL) OR
                           (CD_Organo_Origen IS NOT NULL) THEN
                       (SELECT MAX(LOCALIDAD)
                          FROM REL_INTVSRES RINT
                         WHERE RINT.Ejercicio = ES.Ejercicio
                           AND RINT.TipoES = ES.TipoES
                           AND RINT.Num_Registro = ES.Num_Registro
                         GROUP BY RINT.Ejercicio,
                                  RINT.TipoES,
                                  RINT.Num_Registro)
                    END AS LOCALIDAD,
                    CASE
                      WHEN (CD_Organo_Origen IS NULL AND
                           CD_Dep_Origen IS NULL) OR
                           (CD_Organo_Origen IS NOT NULL) THEN
                       (SELECT MAX(PAIS)
                          FROM REL_INTVSRES RINT
                         WHERE RINT.Ejercicio = ES.Ejercicio
                           AND RINT.TipoES = ES.TipoES
                           AND RINT.Num_Registro = ES.Num_Registro
                         GROUP BY RINT.Ejercicio,
                                  RINT.TipoES,
                                  RINT.Num_Registro)
                    END AS PAIS,
                    CASE
                      WHEN (CD_Organo_Origen IS NULL AND
                           CD_Dep_Origen IS NULL) OR
                           (CD_Organo_Origen IS NOT NULL) THEN
                       (SELECT DS_PROVINCIA
                          FROM AUX_PROVINCIAS
                         WHERE CD_PROVINCIA =
                               (SELECT MAX(PROVINCIA)
                                  FROM REL_INTVSRES RINT
                                 WHERE RINT.Ejercicio = ES.Ejercicio
                                   AND RINT.TipoES = ES.TipoES
                                   AND RINT.Num_Registro = ES.Num_Registro
                                 GROUP BY RINT.Ejercicio,
                                          RINT.TipoES,
                                          RINT.Num_Registro))
                    END AS PROVINCIA,
                    (CASE
                      WHEN CD_Organo_Destino IS NOT NULL AND
                           CD_Dep_Destino IS NOT NULL THEN
                       CASE
                      WHEN OG1.CD_Organosup IS NULL THEN
                       DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
                       NVL((SELECT ' / ' || MAX(Nombre)
                             FROM REL_INTVSRES RINT
                            WHERE RINT.Ejercicio = ES.Ejercicio
                              AND RINT.TipoES = ES.TipoES
                              AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio,
                                     RINT.TipoES,
                                     RINT.Num_Registro),
                           ' ')
                      ELSE
                       DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
                       (SELECT AUX_OrganosV1.DS_Organo
                          FROM AUX_OrganosV1
                         WHERE OG1.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')' ||
                       NVL((SELECT ' / ' || MAX(Nombre)
                             FROM REL_INTVSRES RINT
                            WHERE RINT.Ejercicio = ES.Ejercicio
                              AND RINT.TipoES = ES.TipoES
                              AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio,
                                     RINT.TipoES,
                                     RINT.Num_Registro),
                           ' ')
                    END ELSE CASE
                       WHEN CD_Organo_Destino IS NOT NULL THEN
                        CASE
                       WHEN OG1.CD_Organosup IS NULL THEN
                        OG1.DS_Organo ||
                        NVL((SELECT ' / ' || MAX(Nombre)
                              FROM REL_INTVSRES RINT
                             WHERE RINT.Ejercicio = ES.Ejercicio
                               AND RINT.TipoES = ES.TipoES
                               AND RINT.Num_Registro = ES.Num_Registro
                             GROUP BY RINT.Ejercicio,
                                      RINT.TipoES,
                                      RINT.Num_Registro),
                            ' ')
                       ELSE
                        OG1.DS_Organo || ' (' ||
                        (SELECT AUX_OrganosV1.DS_Organo
                           FROM AUX_OrganosV1
                          WHERE OG1.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')' ||
                        NVL((SELECT ' / ' || MAX(Nombre)
                              FROM REL_INTVSRES RINT
                             WHERE RINT.Ejercicio = ES.Ejercicio
                               AND RINT.TipoES = ES.TipoES
                               AND RINT.Num_Registro = ES.Num_Registro
                             GROUP BY RINT.Ejercicio,
                                      RINT.TipoES,
                                      RINT.Num_Registro),
                            ' ')
                     END ELSE (SELECT MAX(Nombre)
                                 FROM REL_INTVSRES RINT
                                WHERE RINT.Ejercicio = ES.Ejercicio
                                  AND RINT.TipoES = ES.TipoES
                                  AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio,
                                         RINT.TipoES,
                                         RINT.Num_Registro) END END) AS OrgDestino,
                    ' /' || ES.CD_Regis_Original AS CD_Regis_Original,
                    DECODE(ES.Tipo_Regis_Original,
                           'E',
                           ' / Entrada',
                           'S',
                           ' / Salida',
                           '') AS Tipo_Regis_original,

                    ES.Num_Regis_Original,
                    ES.Fecha_Regis_Original,
                    ES.Tipo_Transporte,
                    TR.DS_Transporte,
                    OFI.Direccion,
                    ES.Num_Transporte,
                    ES.Resumen,
                    ES.CD_Organo_Destino
      FROM AUX_OFICINAS      OFI,
           REGISTROES        ES,
           AUX_ORGANOS       OG,
           AUX_ORGANOS       OG1,
           AUX_TRANSPORTES   TR,
           UsuariosV1        US,
           AUX_DEPARTAMENTOS DEP,
           AUX_DEPARTAMENTOS DEP1
     WHERE ES.CD_Oficina = OFI.CD_Oficina
       AND (ES.CD_Division_Origen = OG.CD_Division)
       AND (ES.CD_Organo_Origen = OG.CD_Organo)
       AND (ES.CD_Organo_Origen = DEP.CD_Organo(+))
       AND (ES.CD_Dep_Origen = DEP.CD_Departamento(+))
       AND (ES.CD_Organo_Destino = DEP1.CD_Organo(+))
       AND (ES.CD_Dep_Destino = DEP1.CD_Departamento(+))
       AND (ES.CD_Division_Destino = OG1.CD_Division(+))
       AND (ES.CD_Organo_Destino = OG1.CD_Organo(+))
       AND (ES.Tipo_Transporte = TR.CD_Transporte(+))
       AND ES.Usuario = US.DNI
       AND OFI.CD_Oficina = p
       AND ES.TipoES = 'S'
       AND (ES.Fecha BETWEEN fv AND ff)
       AND (fv IS NOT NULL OR ff IS NOT NULL)
       AND (ES.Num_RegistroOF >= s OR s = 0)
       AND (ES.Num_RegistroOF <= t OR t = 0)
       AND ES.Estado IS NULL
       AND (ES.CD_Organo_Origen = P_Org_Origen OR P_Org_Origen IS NULL)

     ORDER BY OrgOrigen, Num_RegistroOF;

  cursorOut := ret_cursor;
END usp_RPT_IndicesS_fechas_2000;


/*************************************************************************************************/
PROCEDURE usp_RPT_IndicesS_fechas_2(p IN VARCHAR2,
-- fv  in date, ff  in date,
 kk IN VARCHAR2, cc IN VARCHAR2,
 s IN  INTEGER, t IN INTEGER ,u IN VARCHAR2, cursorOut OUT t_cursor) IS

ret_cursor t_cursor;
fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

BEGIN

  OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF )  || '/RG ' ||
		    TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha ,
DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
    (CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN CD_Organo_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo

				ELSE  OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	CASE WHEN (CD_Organo_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
	(SELECT MAX(LOCALIDAD)  FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS LOCALIDAD,
	CASE WHEN  (CD_Organo_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
	(SELECT MAX(PAIS)  FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS PAIS,
	CASE WHEN  (CD_Organo_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
		(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	END AS PROVINCIA,
	(CASE WHEN CD_Organo_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG1.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Organo_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG1.DS_Organo || ' (' || (SELECT AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG1.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
	DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,

	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	OFI.Direccion,
	ES.Num_Transporte,
	ES.Resumen,
	ES.CD_Organo_Destino,
		(select c.DS_Logo
  from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and
    uu.CD_Usuario = u)
   as Organismo
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS OG,  AUX_ORGANOS OG1,AUX_TRANSPORTES TR, UsuariosV1 US,
	DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Division_Origen = OG.CD_Division)
	AND (ES.CD_Organo_Origen = OG.CD_Organo)
	AND (ES.CD_Organo_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_Organo_Destino =DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+))
	AND (ES.CD_Division_Destino=OG1.CD_Division(+))
	AND (ES.CD_Organo_Destino =OG1.CD_Organo(+))
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'S'
	AND (ES.Fecha  BETWEEN fv AND ff)
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	--AND (ES.Num_RegistroOF >= s OR s is Null)
	--AND (ES.Num_RegistroOF <= t OR t is Null)
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND UUU.CD_USUARIO = u
	AND ES.Estado IS NULL


ORDER BY  OrgOrigen,Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_IndicesS_fechas_2;




/*************************************************************************************************/
PROCEDURE usp_RPT_IndicesS_fechas_MIN(p IN VARCHAR2, kk IN VARCHAR2, cc IN VARCHAR2,
 s IN INTEGER, t IN INTEGER, u IN VARCHAR2, cursorOut OUT t_cursor) IS

ret_cursor t_cursor;
fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

BEGIN

OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF )  || '/RG ' ||
		    TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
	END) AS NumRegistro,
	ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha ,
  DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
  (CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN DEP.DS_Departamento || '.' || OG.DS_Organo
			ELSE DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
        AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN OG.DS_Organo
				ELSE OG.DS_Organo || ' (' ||
        (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				 WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
          AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,

	CASE WHEN (ES.CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (ES.CD_Org_Origen IS NOT NULL)
	THEN
	(SELECT MAX(LOCALIDAD) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
	END AS LOCALIDAD,

	CASE WHEN (ES.CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (ES.CD_Org_Origen IS NOT NULL)
	THEN
	(SELECT MAX(PAIS) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
	END AS PAIS,

	CASE WHEN (ES.CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (ES.CD_Org_Origen IS NOT NULL)
	THEN
		(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	END AS PROVINCIA,

	(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_ORGANOS_PROD
			 WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
        AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE OG1.DS_Organo || ' (' ||
        (SELECT DS_Organo FROM AUX_ORGANOS_PROD
         WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
          AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,

	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
	DECODE(ES.Tipo_Regis_Original,'E',' / Entrada','S',' / Salida','') AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	OFI.Direccion,
	ES.Num_Transporte,
	ES.Resumen,
	ES.CD_Org_Destino,
	(select c.DS_Logo
   from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = u)
  as Organismo
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1,
  AUX_TRANSPORTES TR, UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Div_Origen = OG.CD_Division)
	AND (ES.CD_Org_Origen = OG.CD_Organo)
	AND (ES.CD_Org_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_Org_Destino =DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+))
	AND (ES.CD_Div_Destino=OG1.CD_Division(+))
	AND (ES.CD_Org_Destino =OG1.CD_Organo(+))
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'S'
	AND (ES.Fecha BETWEEN fv AND ff)
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	--AND (ES.Num_RegistroOF >= s OR s is Null)
	--AND (ES.Num_RegistroOF <= t OR t is Null)
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND UUU.CD_USUARIO = u
	AND ES.Estado IS NULL

ORDER BY
  OrgOrigen,Num_RegistroOF;

cursorOut:=ret_cursor;

END usp_RPT_IndicesS_fechas_MIN;


/*************************************************************************************************/
PROCEDURE usp_RPT_IndicesS_fechas_MAP(p IN VARCHAR2, kk IN VARCHAR2, cc IN VARCHAR2,
 s IN INTEGER, t IN INTEGER, u IN VARCHAR2, v IN VARCHAR2, w IN VARCHAR2, cursorOut OUT t_cursor) IS

ret_cursor t_cursor;
fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

v_ta varchar(5);

BEGIN

if v is not null then
          v_ta := substr(v,instr(v, '|', 1, 1) + 1,
                                 length(v) - instr(v, '|', 1, 1));
  end if;

OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF )  || '/RG ' ||
		    TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
	END) AS NumRegistro,
	--ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	--ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha ,
  --DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
  (CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN DEP.DS_Departamento || '.' || OG.DS_Organo
			ELSE DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
        AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN OG.DS_Organo
				ELSE OG.DS_Organo || ' (' ||
        (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				 WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
          AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
				END
			ELSE (SELECT Nombre FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro AND RINT.CD_INTERESADO=1)
		END
	END) AS OrgOrigen,
/*
	CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	THEN
	(SELECT MAX(LOCALIDAD) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
	END AS LOCALIDAD,

	CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	THEN
	(SELECT MAX(PAIS) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
	END AS PAIS,

	CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	THEN
		(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	END AS PROVINCIA,
*/
	(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
				NVL((SELECT ' / ' || Nombre FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro AND RINT.CD_INTERESADO=1), ' ')
			ELSE DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_ORGANOS_PROD
			 WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
        AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
				NVL((SELECT ' / ' || Nombre FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro AND RINT.CD_INTERESADO=1), ' ')
			END
	ELSE
		CASE WHEN CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo ||
					NVL((SELECT ' / ' || Nombre FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro AND RINT.CD_INTERESADO=1), ' ')
				ELSE OG1.DS_Organo || ' (' ||
        (SELECT DS_Organo FROM AUX_ORGANOS_PROD
         WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
          AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
					NVL((SELECT ' / ' || Nombre FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro AND RINT.CD_INTERESADO=1), ' ')
				END
			ELSE (SELECT Nombre FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro AND RINT.CD_INTERESADO=1)
		END
	END) AS OrgDestino,

	--' /' || ES.CD_Regis_Original AS CD_Regis_Original,
	--DECODE(ES.Tipo_Regis_Original,'E',' / Entrada','S',' / Salida','') AS Tipo_Regis_original,
	--ES.Num_Regis_Original,
	--ES.Fecha_Regis_Original,
	--ES.Tipo_Transporte,
	--TR.DS_Transporte,
	--OFI.Direccion,
	--ES.Num_Transporte,
	ES.Resumen,
	ES.CD_Org_Destino,
	(select c.DS_Logo
   from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = u)
  as Organismo,
  CASE WHEN v_ta IS NOT NULL
	   THEN CM.DS_COMENTARIO
	   ELSE ''
	END AS TipoAsunto

FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1,
  AUX_TRANSPORTES TR, UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, AUX_COMENTARIOS CM
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Div_Origen = OG.CD_Division)
	AND (ES.CD_Org_Origen = OG.CD_Organo)
	AND (ES.CD_Org_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_Org_Destino =DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+))
	AND (ES.CD_Div_Destino=OG1.CD_Division(+))
	AND (ES.CD_Org_Destino =OG1.CD_Organo(+))
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
	AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'S'
	AND (ES.Fecha BETWEEN fv AND ff)
	--AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0 OR s = '' OR s IS NULL)
	AND (ES.Num_RegistroOF <= t OR t = 0 OR t = '' OR t IS NULL)
	--AND (ES.Num_RegistroOF >= s OR s is Null)
	--AND (ES.Num_RegistroOF <= t OR t is Null)
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND UUU.CD_USUARIO = u
	AND ES.Estado IS NULL
	AND ((v_ta IS NOT NULL AND TO_CHAR(ES.CD_TIPOASUNTO_COM) = v_ta) OR
               (v_ta is null))
  AND (DEP.CD_DEPARTAMENTO = w OR w is null)

ORDER BY
  OrgOrigen,Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_IndicesS_fechas_MAP;

/*************************************************************************************************/

PROCEDURE usp_RPT_IndicesS_FPre_2200(p IN VARCHAR2, kk IN VARCHAR2, cc IN VARCHAR2,
 s IN INTEGER, t IN INTEGER, u IN VARCHAR2, v IN VARCHAR2, cursorOut OUT t_cursor) IS

ret_cursor t_cursor;
fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

v_ta varchar(5);

BEGIN

if v is not null then
          v_ta := substr(v,instr(v, '|', 1, 1) + 1,
                                 length(v) - instr(v, '|', 1, 1));
  end if;

OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF )  || '/RG ' ||
		    TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
	END) AS NumRegistro,
	--ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	--ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.Fecha_Presen AS Fecha ,
  --DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
  (CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN DEP.DS_Departamento || '.' || OG.DS_Organo
			ELSE DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
        AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN OG.DS_Organo
				ELSE OG.DS_Organo || ' (' ||
        (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				 WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
          AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
/*
	CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	THEN
	(SELECT MAX(LOCALIDAD) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
	END AS LOCALIDAD,

	CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	THEN
	(SELECT MAX(PAIS) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
	END AS PAIS,

	CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	THEN
		(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	END AS PROVINCIA,
*/
	(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_ORGANOS_PROD
			 WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
        AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE OG1.DS_Organo || ' (' ||
        (SELECT DS_Organo FROM AUX_ORGANOS_PROD
         WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
          AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,

	--' /' || ES.CD_Regis_Original AS CD_Regis_Original,
	--DECODE(ES.Tipo_Regis_Original,'E',' / Entrada','S',' / Salida','') AS Tipo_Regis_original,
	--ES.Num_Regis_Original,
	--ES.Fecha_Regis_Original,
	--ES.Tipo_Transporte,
	--TR.DS_Transporte,
	--OFI.Direccion,
	--ES.Num_Transporte,
	ES.Resumen,
	ES.CD_Org_Destino,
	(select c.DS_Logo
   from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = u)
  as Organismo,
  CASE WHEN v_ta IS NOT NULL
	   THEN CM.DS_COMENTARIO
	   ELSE ''
	END AS TipoAsunto

FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1,
  AUX_TRANSPORTES TR, UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, AUX_COMENTARIOS CM
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Div_Origen = OG.CD_Division)
	AND (ES.CD_Org_Origen = OG.CD_Organo)
	AND (ES.CD_Org_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_Org_Destino =DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+))
	AND (ES.CD_Div_Destino=OG1.CD_Division(+))
	AND (ES.CD_Org_Destino =OG1.CD_Organo(+))
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
	AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'S'
  --AND (ES.Fecha_Presen BETWEEN fv AND ff)
  --AND (TRUNC(ES.Fecha_presen) >= fv and trunc(ES.Fecha_presen) <= ff)
  AND (ES.Fecha_presen_sh >= fv and ES.Fecha_presen_sh <= ff)
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0 OR s = '' OR s IS NULL)
	AND (ES.Num_RegistroOF <= t OR t = 0 OR t = '' OR t IS NULL)
	--AND (ES.Num_RegistroOF >= s OR s is Null)
	--AND (ES.Num_RegistroOF <= t OR t is Null)
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND UUU.CD_USUARIO = u
	AND ES.Estado IS NULL
	AND ((v_ta IS NOT NULL AND TO_CHAR(ES.CD_TIPOASUNTO_COM) = v_ta) OR
               (v_ta is null))

ORDER BY
  OrgOrigen,Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_IndicesS_FPre_2200;

/*************************************************************************************************/
PROCEDURE usp_RPT_ENT_ORI_MAP(p IN VARCHAR2, kk IN VARCHAR2, cc IN VARCHAR2,
 s IN INTEGER, t IN INTEGER, u IN VARCHAR2, cursorOut OUT t_cursor) IS

ret_cursor t_cursor;
fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

BEGIN

OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF )  || '/RG ' ||
		    TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
	END) AS NumRegistro,
	--ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	--ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha ,
  --DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
  (CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN DEP.DS_Departamento || '.' || OG.DS_Organo
			ELSE DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
        AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN OG.DS_Organo
				ELSE OG.DS_Organo || ' (' ||
        (SELECT DS_Organo FROM AUX_ORGANOS_PROD
				 WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
          AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
/*
	CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	THEN
	(SELECT MAX(LOCALIDAD) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
	END AS LOCALIDAD,

	CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	THEN
	(SELECT MAX(PAIS) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
	END AS PAIS,

	CASE WHEN (CD_Org_Origen IS NULL AND CD_Dep_Origen IS NULL) OR (CD_Org_Origen IS NOT NULL)
	THEN
		(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	END AS PROVINCIA,
*/
	(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_ORGANOS_PROD
			 WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
        AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE OG1.DS_Organo || ' (' ||
        (SELECT DS_Organo FROM AUX_ORGANOS_PROD
         WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
          AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,

	--' /' || ES.CD_Regis_Original AS CD_Regis_Original,
	--DECODE(ES.Tipo_Regis_Original,'E',' / Entrada','S',' / Salida','') AS Tipo_Regis_original,
	--ES.Num_Regis_Original,
	--ES.Fecha_Regis_Original,
	--ES.Tipo_Transporte,
	--TR.DS_Transporte,
	--OFI.Direccion,
	--ES.Num_Transporte,
	ES.Resumen,
	ES.CD_Org_Destino,
	(select c.DS_Logo
   from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = u)
  as Organismo
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1,
  AUX_TRANSPORTES TR, UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Div_Origen = OG.CD_Division)
	AND (ES.CD_Org_Origen = OG.CD_Organo)
	AND (ES.CD_Org_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_Org_Destino =DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+))
	AND (ES.CD_Div_Destino=OG1.CD_Division(+))
	AND (ES.CD_Org_Destino =OG1.CD_Organo(+))
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND (ES.Fecha BETWEEN fv AND ff)
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	--AND (ES.Num_RegistroOF >= s OR s is Null)
	--AND (ES.Num_RegistroOF <= t OR t is Null)
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND UUU.CD_USUARIO = u
	AND ES.Estado IS NULL

ORDER BY
  OrgOrigen,Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_ENT_ORI_MAP;


/*************************************************************************************************/
procedure usp_RPT_IndicesSD_fechas(p         IN VARCHAR2,
                                   kk        IN VARCHAR2,
                                   cc        IN VARCHAR2,
                                   s         IN INTEGER,
                                   t         IN INTEGER,
                                   cursorOut OUT t_cursor) IS

  ret_cursor t_cursor;
  fv         DATE := TO_DATE(kk, 'DD/MM/YYYY');
  ff         DATE := TO_DATE(cc, 'DD/MM/YYYY');

BEGIN

  OPEN ret_cursor FOR

    SELECT DISTINCT fv AS fechai,
                    ff AS fechaf,
                    ES.Num_RegistroOF,
                    (CASE
                      WHEN (SELECT Num_RG
                              FROM REGISTROGENERAL RG
                             WHERE RG.Ejercicio = ES.Ejercicio
                               AND RG.TipoES = ES.TipoES
                               AND RG.CD_Oficina = ES.CD_Oficina
                               AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL THEN
                       --TO_CHAR(ES.Num_RegistroOF) || '/RG ' ||
                       TO_CHAR(ES.Num_RegistroOF) || NVL2(OFI.SIGLAOF,' (' || OFI.SIGLAOF || ') ','') || '/RG ' ||
                       TO_CHAR((SELECT Num_RG
                                 FROM REGISTROGENERAL RG
                                WHERE RG.Ejercicio = ES.Ejercicio
                                  AND RG.TipoES = ES.TipoES
                                  AND RG.CD_Oficina = ES.CD_Oficina
                                  AND RG.Num_Registro = ES.Num_RegistroOF))
                      ELSE
                       --TO_CHAR(ES.Num_RegistroOF)
                       TO_CHAR(ES.Num_RegistroOF) || NVL2(OFI.SIGLAOF,' (' || OFI.SIGLAOF || ') ','')
                    END) AS NumRegistro,
                    ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
                    ES.FECHA_REGIS_ORIGINAL AS Fecharem,
                    OFI.DS_Oficina,
                    ES.FechaP AS Fecha,
                    DECODE(ES.TipoES, 'E', 'Entrada', 'S', 'Salida', '') AS TipoES,
                    US.DS_Usuario,
                    (CASE
                      WHEN CD_Organo_Origen IS NOT NULL AND
                           CD_Dep_Origen IS NOT NULL THEN
                       CASE
                      WHEN OG.CD_Organosup IS NULL THEN
                       DEP.DS_Departamento || '.' || OG.DS_Organo
                      ELSE
                       DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
                       (SELECT AUX_OrganosV1.DS_Organo
                          FROM AUX_OrganosV1
                         WHERE OG.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')'
                    END ELSE CASE
                       WHEN CD_Organo_Origen IS NOT NULL THEN
                        CASE
                       WHEN OG.CD_Organosup IS NULL THEN
                        OG.DS_Organo

                       ELSE
                        OG.DS_Organo || ' (' ||
                        (SELECT AUX_OrganosV1.DS_Organo
                           FROM AUX_OrganosV1
                          WHERE OG.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')'
                     END ELSE (SELECT MAX(Nombre)
                                 FROM REL_INTVSRES RINT
                                WHERE RINT.Ejercicio = ES.Ejercicio
                                  AND RINT.TipoES = ES.TipoES
                                  AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio,
                                         RINT.TipoES,
                                         RINT.Num_Registro) END END) AS OrgOrigen,
                    (CASE
                      WHEN CD_Organo_Destino IS NOT NULL AND
                           CD_Dep_Destino IS NOT NULL THEN
                       CASE
                      WHEN OG1.CD_Organosup IS NULL THEN
                       DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
                       NVL((SELECT ' / ' || MAX(Nombre)
                             FROM REL_INTVSRES RINT
                            WHERE RINT.Ejercicio = ES.Ejercicio
                              AND RINT.TipoES = ES.TipoES
                              AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio,
                                     RINT.TipoES,
                                     RINT.Num_Registro),
                           ' ')
                      ELSE
                       DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
                       (SELECT AUX_OrganosV1.DS_Organo
                          FROM AUX_OrganosV1
                         WHERE OG1.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')' ||
                       NVL((SELECT ' / ' || MAX(Nombre)
                             FROM REL_INTVSRES RINT
                            WHERE RINT.Ejercicio = ES.Ejercicio
                              AND RINT.TipoES = ES.TipoES
                              AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio,
                                     RINT.TipoES,
                                     RINT.Num_Registro),
                           ' ')
                    END ELSE CASE
                       WHEN CD_Organo_Destino IS NOT NULL THEN
                        CASE
                       WHEN OG1.CD_Organosup IS NULL THEN
                        OG1.DS_Organo ||
                        NVL((SELECT ' / ' || MAX(Nombre)
                              FROM REL_INTVSRES RINT
                             WHERE RINT.Ejercicio = ES.Ejercicio
                               AND RINT.TipoES = ES.TipoES
                               AND RINT.Num_Registro = ES.Num_Registro
                             GROUP BY RINT.Ejercicio,
                                      RINT.TipoES,
                                      RINT.Num_Registro),
                            ' ')
                       ELSE
                        OG1.DS_Organo || ' (' ||
                        (SELECT AUX_OrganosV1.DS_Organo
                           FROM AUX_OrganosV1
                          WHERE OG1.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')' ||
                        NVL((SELECT ' / ' || MAX(Nombre)
                              FROM REL_INTVSRES RINT
                             WHERE RINT.Ejercicio = ES.Ejercicio
                               AND RINT.TipoES = ES.TipoES
                               AND RINT.Num_Registro = ES.Num_Registro
                             GROUP BY RINT.Ejercicio,
                                      RINT.TipoES,
                                      RINT.Num_Registro),
                            ' ')
                     END ELSE (SELECT MAX(Nombre)
                                 FROM REL_INTVSRES RINT
                                WHERE RINT.Ejercicio = ES.Ejercicio
                                  AND RINT.TipoES = ES.TipoES
                                  AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio,
                                         RINT.TipoES,
                                         RINT.Num_Registro) END END) AS OrgDestino,
                    CASE
                      WHEN (CD_Organo_Destino IS NULL AND
                           CD_Dep_Destino IS NULL) OR
                           (CD_Organo_Destino IS NOT NULL) THEN
                       (SELECT MAX(LOCALIDAD)
                          FROM REL_INTVSRES RINT
                         WHERE RINT.Ejercicio = ES.Ejercicio
                           AND RINT.TipoES = ES.TipoES
                           AND RINT.Num_Registro = ES.Num_Registro
                         GROUP BY RINT.Ejercicio,
                                  RINT.TipoES,
                                  RINT.Num_Registro)
                    END AS LOCALIDAD,
                    CASE
                      WHEN (CD_Organo_Destino IS NULL AND
                           CD_Dep_Destino IS NULL) OR
                           (CD_Organo_Destino IS NOT NULL) THEN
                       (SELECT MAX(PAIS)
                          FROM REL_INTVSRES RINT
                         WHERE RINT.Ejercicio = ES.Ejercicio
                           AND RINT.TipoES = ES.TipoES
                           AND RINT.Num_Registro = ES.Num_Registro
                         GROUP BY RINT.Ejercicio,
                                  RINT.TipoES,
                                  RINT.Num_Registro)
                    END AS PAIS,
                    CASE
                      WHEN (CD_Organo_Destino IS NULL AND
                           CD_Dep_Destino IS NULL) OR
                           (CD_Organo_Destino IS NOT NULL) THEN
                       (SELECT DS_PROVINCIA
                          FROM AUX_PROVINCIAS
                         WHERE CD_PROVINCIA =
                               (SELECT MAX(PROVINCIA)
                                  FROM REL_INTVSRES RINT
                                 WHERE RINT.Ejercicio = ES.Ejercicio
                                   AND RINT.TipoES = ES.TipoES
                                   AND RINT.Num_Registro = ES.Num_Registro
                                 GROUP BY RINT.Ejercicio,
                                          RINT.TipoES,
                                          RINT.Num_Registro))
                    END AS PROVINCIA,

                    ' /' || ES.CD_Regis_Original AS CD_Regis_Original,
                    DECODE(ES.Tipo_Regis_Original,
                           'E',
                           ' / Entrada',
                           'S',
                           ' / Salida',
                           '') AS Tipo_Regis_original,
                    ES.Num_Regis_Original,
                    ES.Fecha_Regis_Original,
                    ES.Tipo_Transporte,
                    TR.DS_Transporte,
                    OFI.Direccion,
                    ES.Num_Transporte,
                    ES.Resumen,
                    ES.CD_Organo_Destino
      FROM AUX_OFICINAS      OFI,
           REGISTROES        ES,
           AUX_ORGANOS       OG,
           AUX_ORGANOS       OG1,
           AUX_TRANSPORTES   TR,
           UsuariosV1        US,
           AUX_DEPARTAMENTOS DEP,
           AUX_DEPARTAMENTOS DEP1
     WHERE ES.CD_Oficina = OFI.CD_Oficina
       AND (ES.CD_Division_Origen = OG.CD_Division)
       AND (ES.CD_Organo_Origen = OG.CD_Organo)
       AND (ES.CD_Organo_Origen = DEP.CD_Organo(+))
       AND (ES.CD_Dep_Origen = DEP.CD_Departamento(+))
       AND (ES.CD_Organo_Destino = DEP1.CD_Organo(+))
       AND (ES.CD_Dep_Destino = DEP1.CD_Departamento(+))
       AND (ES.CD_Division_Destino = OG1.CD_Division(+))
       AND (ES.CD_Organo_Destino = OG1.CD_Organo(+))
       AND (ES.Tipo_Transporte = TR.CD_Transporte(+))
       AND ES.Usuario = US.DNI
       AND OFI.CD_Oficina = p
       AND ES.TipoES = 'S'
       AND (ES.Fecha BETWEEN fv AND ff)
       AND (fv IS NOT NULL OR ff IS NOT NULL)
       AND (ES.Num_RegistroOF >= s OR s = 0)
       AND (ES.Num_RegistroOF <= t OR t = 0)
       AND ES.Estado IS NULL
       AND ES.CD_Organo_Destino IS NOT NULL

     ORDER BY OrgDestino, Num_RegistroOF;

  cursorOut := ret_cursor;
END usp_RPT_IndicesSD_fechas;

/*************************************************************************************************/

procedure usp_RPT_IndicesSD_fechas_2000(p         IN VARCHAR2,
                                   kk        IN VARCHAR2,
                                   cc        IN VARCHAR2,
                                   s         IN INTEGER,
                                   t         IN INTEGER,
                                   P_Org_Destino IN VARCHAR2,
                                   cursorOut OUT t_cursor) IS

  ret_cursor t_cursor;
  fv         DATE := TO_DATE(kk, 'DD/MM/YYYY');
  ff         DATE := TO_DATE(cc, 'DD/MM/YYYY');

BEGIN

  OPEN ret_cursor FOR

    SELECT DISTINCT fv AS fechai,
                    ff AS fechaf,
                    ES.Num_RegistroOF,
                    (CASE
                      WHEN (SELECT Num_RG
                              FROM REGISTROGENERAL RG
                             WHERE RG.Ejercicio = ES.Ejercicio
                               AND RG.TipoES = ES.TipoES
                               AND RG.CD_Oficina = ES.CD_Oficina
                               AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL THEN
                       --TO_CHAR(ES.Num_RegistroOF) || '/RG ' ||
                       TO_CHAR(ES.Num_RegistroOF) || NVL2(OFI.SIGLAOF,' (' || OFI.SIGLAOF || ') ','') || '/RG ' ||
                       TO_CHAR((SELECT Num_RG
                                 FROM REGISTROGENERAL RG
                                WHERE RG.Ejercicio = ES.Ejercicio
                                  AND RG.TipoES = ES.TipoES
                                  AND RG.CD_Oficina = ES.CD_Oficina
                                  AND RG.Num_Registro = ES.Num_RegistroOF))
                      ELSE
                       --TO_CHAR(ES.Num_RegistroOF)
                       TO_CHAR(ES.Num_RegistroOF) || NVL2(OFI.SIGLAOF,' (' || OFI.SIGLAOF || ') ','')
                    END) AS NumRegistro,
                    ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
                    ES.FECHA_REGIS_ORIGINAL AS Fecharem,
                    OFI.DS_Oficina,
                    ES.FechaP AS Fecha,
                    DECODE(ES.TipoES, 'E', 'Entrada', 'S', 'Salida', '') AS TipoES,
                    US.DS_Usuario,
                    (CASE
                      WHEN CD_Organo_Origen IS NOT NULL AND
                           CD_Dep_Origen IS NOT NULL THEN
                       CASE
                      WHEN OG.CD_Organosup IS NULL THEN
                       DEP.DS_Departamento || '.' || OG.DS_Organo
                      ELSE
                       DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
                       (SELECT AUX_OrganosV1.DS_Organo
                          FROM AUX_OrganosV1
                         WHERE OG.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')'
                    END ELSE CASE
                       WHEN CD_Organo_Origen IS NOT NULL THEN
                        CASE
                       WHEN OG.CD_Organosup IS NULL THEN
                        OG.DS_Organo

                       ELSE
                        OG.DS_Organo || ' (' ||
                        (SELECT AUX_OrganosV1.DS_Organo
                           FROM AUX_OrganosV1
                          WHERE OG.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')'
                     END ELSE (SELECT MAX(Nombre)
                                 FROM REL_INTVSRES RINT
                                WHERE RINT.Ejercicio = ES.Ejercicio
                                  AND RINT.TipoES = ES.TipoES
                                  AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio,
                                         RINT.TipoES,
                                         RINT.Num_Registro) END END) AS OrgOrigen,
                    (CASE
                      WHEN CD_Organo_Destino IS NOT NULL AND
                           CD_Dep_Destino IS NOT NULL THEN
                       CASE
                      WHEN OG1.CD_Organosup IS NULL THEN
                       DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
                       NVL((SELECT ' / ' || MAX(Nombre)
                             FROM REL_INTVSRES RINT
                            WHERE RINT.Ejercicio = ES.Ejercicio
                              AND RINT.TipoES = ES.TipoES
                              AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio,
                                     RINT.TipoES,
                                     RINT.Num_Registro),
                           ' ')
                      ELSE
                       DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
                       (SELECT AUX_OrganosV1.DS_Organo
                          FROM AUX_OrganosV1
                         WHERE OG1.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')' ||
                       NVL((SELECT ' / ' || MAX(Nombre)
                             FROM REL_INTVSRES RINT
                            WHERE RINT.Ejercicio = ES.Ejercicio
                              AND RINT.TipoES = ES.TipoES
                              AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio,
                                     RINT.TipoES,
                                     RINT.Num_Registro),
                           ' ')
                    END ELSE CASE
                       WHEN CD_Organo_Destino IS NOT NULL THEN
                        CASE
                       WHEN OG1.CD_Organosup IS NULL THEN
                        OG1.DS_Organo ||
                        NVL((SELECT ' / ' || MAX(Nombre)
                              FROM REL_INTVSRES RINT
                             WHERE RINT.Ejercicio = ES.Ejercicio
                               AND RINT.TipoES = ES.TipoES
                               AND RINT.Num_Registro = ES.Num_Registro
                             GROUP BY RINT.Ejercicio,
                                      RINT.TipoES,
                                      RINT.Num_Registro),
                            ' ')
                       ELSE
                        OG1.DS_Organo || ' (' ||
                        (SELECT AUX_OrganosV1.DS_Organo
                           FROM AUX_OrganosV1
                          WHERE OG1.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')' ||
                        NVL((SELECT ' / ' || MAX(Nombre)
                              FROM REL_INTVSRES RINT
                             WHERE RINT.Ejercicio = ES.Ejercicio
                               AND RINT.TipoES = ES.TipoES
                               AND RINT.Num_Registro = ES.Num_Registro
                             GROUP BY RINT.Ejercicio,
                                      RINT.TipoES,
                                      RINT.Num_Registro),
                            ' ')
                     END ELSE (SELECT MAX(Nombre)
                                 FROM REL_INTVSRES RINT
                                WHERE RINT.Ejercicio = ES.Ejercicio
                                  AND RINT.TipoES = ES.TipoES
                                  AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio,
                                         RINT.TipoES,
                                         RINT.Num_Registro) END END) AS OrgDestino,
                    CASE
                      WHEN (CD_Organo_Destino IS NULL AND
                           CD_Dep_Destino IS NULL) OR
                           (CD_Organo_Destino IS NOT NULL) THEN
                       (SELECT MAX(LOCALIDAD)
                          FROM REL_INTVSRES RINT
                         WHERE RINT.Ejercicio = ES.Ejercicio
                           AND RINT.TipoES = ES.TipoES
                           AND RINT.Num_Registro = ES.Num_Registro
                         GROUP BY RINT.Ejercicio,
                                  RINT.TipoES,
                                  RINT.Num_Registro)
                    END AS LOCALIDAD,
                    CASE
                      WHEN (CD_Organo_Destino IS NULL AND
                           CD_Dep_Destino IS NULL) OR
                           (CD_Organo_Destino IS NOT NULL) THEN
                       (SELECT MAX(PAIS)
                          FROM REL_INTVSRES RINT
                         WHERE RINT.Ejercicio = ES.Ejercicio
                           AND RINT.TipoES = ES.TipoES
                           AND RINT.Num_Registro = ES.Num_Registro
                         GROUP BY RINT.Ejercicio,
                                  RINT.TipoES,
                                  RINT.Num_Registro)
                    END AS PAIS,
                    CASE
                      WHEN (CD_Organo_Destino IS NULL AND
                           CD_Dep_Destino IS NULL) OR
                           (CD_Organo_Destino IS NOT NULL) THEN
                       (SELECT DS_PROVINCIA
                          FROM AUX_PROVINCIAS
                         WHERE CD_PROVINCIA =
                               (SELECT MAX(PROVINCIA)
                                  FROM REL_INTVSRES RINT
                                 WHERE RINT.Ejercicio = ES.Ejercicio
                                   AND RINT.TipoES = ES.TipoES
                                   AND RINT.Num_Registro = ES.Num_Registro
                                 GROUP BY RINT.Ejercicio,
                                          RINT.TipoES,
                                          RINT.Num_Registro))
                    END AS PROVINCIA,

                    ' /' || ES.CD_Regis_Original AS CD_Regis_Original,
                    DECODE(ES.Tipo_Regis_Original,
                           'E',
                           ' / Entrada',
                           'S',
                           ' / Salida',
                           '') AS Tipo_Regis_original,
                    ES.Num_Regis_Original,
                    ES.Fecha_Regis_Original,
                    ES.Tipo_Transporte,
                    TR.DS_Transporte,
                    OFI.Direccion,
                    ES.Num_Transporte,
                    ES.Resumen,
                    ES.CD_Organo_Destino
      FROM AUX_OFICINAS      OFI,
           REGISTROES        ES,
           AUX_ORGANOS       OG,
           AUX_ORGANOS       OG1,
           AUX_TRANSPORTES   TR,
           UsuariosV1        US,
           AUX_DEPARTAMENTOS DEP,
           AUX_DEPARTAMENTOS DEP1
     WHERE ES.CD_Oficina = OFI.CD_Oficina
       AND (ES.CD_Division_Origen = OG.CD_Division)
       AND (ES.CD_Organo_Origen = OG.CD_Organo)
       AND (ES.CD_Organo_Origen = DEP.CD_Organo(+))
       AND (ES.CD_Dep_Origen = DEP.CD_Departamento(+))
       AND (ES.CD_Organo_Destino = DEP1.CD_Organo(+))
       AND (ES.CD_Dep_Destino = DEP1.CD_Departamento(+))
       AND (ES.CD_Division_Destino = OG1.CD_Division(+))
       AND (ES.CD_Organo_Destino = OG1.CD_Organo(+))
       AND (ES.Tipo_Transporte = TR.CD_Transporte(+))
       AND ES.Usuario = US.DNI
       AND OFI.CD_Oficina = p
       AND ES.TipoES = 'S'
       AND (ES.Fecha BETWEEN fv AND ff)
       AND (fv IS NOT NULL OR ff IS NOT NULL)
       AND (ES.Num_RegistroOF >= s OR s = 0)
       AND (ES.Num_RegistroOF <= t OR t = 0)
       AND ES.Estado IS NULL
       AND (ES.CD_Organo_Destino = P_Org_Destino OR P_Org_Destino IS NULL)

     ORDER BY OrgDestino, Num_RegistroOF;

  cursorOut := ret_cursor;
END usp_RPT_IndicesSD_fechas_2000;

/*************************************************************************************************/
PROCEDURE usp_RPT_IndicesSD_fechas_2(p IN VARCHAR2,
--fv in date, ff in date,
kk IN VARCHAR2, cc IN VARCHAR2,
s IN INTEGER,t IN INTEGER , u IN VARCHAR2, cursorOut OUT t_cursor) IS

  ret_cursor t_cursor;
fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

 BEGIN

  OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
    (CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
		ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha ,
			DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
    (CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN CD_Organo_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo

				ELSE  OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Organo_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG1.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Organo_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo ||
					NVL((SELECT ' / ' || MAX(UPPER(Nombre)) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG1.DS_Organo || ' (' || (SELECT AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					UPPER(OG1.CD_Organosup)=UPPER(AUX_OrganosV1.CD_Organo(+))) || ')' ||
          --OG1.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(UPPER(Nombre)) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(UPPER(Nombre)) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	CASE WHEN (CD_Organo_Destino  IS NULL  AND CD_Dep_Destino IS NULL)  OR  (CD_Organo_Destino IS NOT NULL)
	THEN
	(SELECT MAX(LOCALIDAD)  FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS LOCALIDAD,
	CASE WHEN  (CD_Organo_Destino  IS NULL  AND CD_Dep_Destino IS NULL)  OR  (CD_Organo_Destino IS NOT NULL)
	THEN
	(SELECT MAX(PAIS)  FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS PAIS,
	CASE WHEN  (CD_Organo_Destino  IS NULL  AND CD_Dep_Destino IS NULL)  OR  (CD_Organo_Destino IS NOT NULL)
	THEN
		(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	END AS PROVINCIA,

	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	OFI.Direccion,
	ES.Num_Transporte,
	ES.Resumen,
	ES.CD_Organo_Destino,
		(select c.DS_Logo
  from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and
    uu.CD_Usuario = u)
   as Organismo
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS OG,  AUX_ORGANOS OG1,AUX_TRANSPORTES TR, UsuariosV1 US,
	DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Division_Origen = OG.CD_Division)
	AND (ES.CD_Organo_Origen = OG.CD_Organo)
	AND (ES.CD_Organo_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_Organo_Destino =DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+))
	AND (ES.CD_Division_Destino=OG1.CD_Division(+))
	AND (UPPER(ES.CD_Organo_Destino)=UPPER(OG1.CD_Organo(+)))
  --AND (ES.CD_Organo_Destino=OG1.CD_Organo(+))
	AND (ES.Tipo_Transporte =TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'S'
	AND (ES.Fecha  BETWEEN fv AND ff)
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	-- AND (ES.Num_RegistroOF >= s OR s is Null)
	-- AND (ES.Num_RegistroOF <= t OR t is Null)
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND UUU.CD_USUARIO = u
	AND ES.Estado IS NULL
	AND ES.CD_Organo_Destino IS  NOT NULL

ORDER BY  OrgDestino,Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_IndicesSD_fechas_2;



/*************************************************************************************************/
PROCEDURE usp_RPT_IndicesSD_fechas_MAP
(p IN VARCHAR2,
 kk IN VARCHAR2,
 cc IN VARCHAR2,
 s IN INTEGER,
 t IN INTEGER,
 u IN VARCHAR2,
 v IN INTEGER,
 w IN VARCHAR2,
 x IN VARCHAR2,  -- Tipo Asunto
 y IN VARCHAR2,  -- Subórgano
 cursorOut OUT t_cursor)
IS

ret_cursor t_cursor;
fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

v_ta varchar(5);

BEGIN

if x is not null then
          v_ta := substr(x,instr(x, '|', 1, 1) + 1,
                                 length(x) - instr(x, '|', 1, 1));
  end if;

OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
  (CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
	 END) AS NumRegistro,
	ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha ,
	DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
  (CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN DEP.DS_Departamento || '.' || OG.DS_Organo
			ELSE DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_Organos_PROD
			 WHERE OG.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
        AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN OG.DS_Organo
				ELSE OG.DS_Organo || ' (' ||
        (SELECT DS_Organo FROM AUX_Organos_PROD
         WHERE OG.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
          AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,

	(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_Organos_PROD
			 WHERE OG1.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
        AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN OG1.DS_Organo ||
					NVL((SELECT ' / ' || MAX(UPPER(Nombre)) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE OG1.DS_Organo || ' (' ||
        (SELECT DS_Organo FROM AUX_Organos_PROD
					WHERE OG1.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
          AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(UPPER(Nombre)) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(UPPER(Nombre)) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,

	CASE WHEN (CD_Org_Destino IS NULL AND CD_Dep_Destino IS NULL) OR (CD_Org_Destino IS NOT NULL)
	THEN
	(SELECT MAX(LOCALIDAD) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS LOCALIDAD,

	CASE WHEN (CD_Org_Destino IS NULL AND CD_Dep_Destino IS NULL) OR (CD_Org_Destino IS NOT NULL)
	THEN
	(SELECT MAX(PAIS) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS PAIS,

	CASE WHEN (CD_Org_Destino IS NULL AND CD_Dep_Destino IS NULL) OR (CD_Org_Destino IS NOT NULL)
	THEN
		(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	END AS PROVINCIA,

	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
  DECODE(ES.Tipo_Regis_Original,'E',' / Entrada','S',' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	OFI.Direccion,
	ES.Num_Transporte,
	NVL(CM.CONTENIDO,' ') || ' ' || ES.Resumen AS Resumen,
	ES.CD_Org_Destino,
	(select c.DS_Logo
   from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = u)
   as Organismo,
   CASE WHEN v_ta IS NOT NULL
	   THEN CM.DS_COMENTARIO
	   ELSE ''
	END AS TipoAsunto


FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1,
  AUX_TRANSPORTES TR, UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, AUX_COMENTARIOS CM
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Div_Origen = OG.CD_Division)
	AND (ES.CD_Org_Origen = OG.CD_Organo)
	AND (ES.CD_Org_Origen = DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen = DEP.CD_Departamento(+))
	AND (ES.CD_Org_Destino = DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino = DEP1.CD_Departamento(+))
	AND (ES.CD_Div_Destino = OG1.CD_Division(+))
	AND (UPPER(ES.CD_Org_Destino) = UPPER(OG1.CD_Organo(+)))
	AND (ES.Tipo_Transporte =TR.CD_Transporte(+))
	AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
	AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'S'
	AND (ES.Fecha  BETWEEN fv AND ff)
	--AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	-- AND (ES.Num_RegistroOF >= s OR s is Null)
	-- AND (ES.Num_RegistroOF <= t OR t is Null)
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND UUU.CD_USUARIO = u
	AND ES.Estado IS NULL
	AND ES.CD_Org_Destino IS NOT NULL
  AND ( (ES.CD_Org_Destino  = w OR w is null) OR
        ((ES.CD_Org_Destino IN
        	 (SELECT CD_Organo FROM AUX_ORGANOS_PROD WHERE CD_Oficina =
           	  (SELECT CD_Oficina FROM AUX_ORGANOS_PROD WHERE CD_Organo = w and CD_Division = v)) OR w is null)))
   AND (ES.CD_DIV_DESTINO = v OR v IS NULL OR v = 0 OR v = '')
  ----
  --AND (ES.CD_DIV_DESTINO = v OR v IS NULL)
  AND ((v_ta IS NOT NULL AND TO_CHAR(ES.CD_TIPOASUNTO_COM) = v_ta) OR
               (v_ta is null))
  AND (DEP1.CD_DEPARTAMENTO = y or y is null)

ORDER BY
  OrgDestino, Num_RegistroOF;

cursorOut:=ret_cursor;

END usp_RPT_IndicesSD_fechas_MAP;

/*************************************************************************************************/

PROCEDURE usp_RPT_IndicesSD_FPre_2200
(p IN VARCHAR2,
 kk IN VARCHAR2,
 cc IN VARCHAR2,
 s IN INTEGER,
 t IN INTEGER,
 u IN VARCHAR2,
 v IN INTEGER,
 w IN VARCHAR2,
 x IN VARCHAR2,  -- Tipo Asunto
 cursorOut OUT t_cursor)
IS

ret_cursor t_cursor;
fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

v_ta varchar(5);

BEGIN

if x is not null then
          v_ta := substr(x,instr(x, '|', 1, 1) + 1,
                                 length(x) - instr(x, '|', 1, 1));
  end if;

OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
  (CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
	 END) AS NumRegistro,
	ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.Fecha_Presen AS Fecha ,
	DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
  (CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN DEP.DS_Departamento || '.' || OG.DS_Organo
			ELSE DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_Organos_PROD
			 WHERE OG.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
        AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN OG.DS_Organo
				ELSE OG.DS_Organo || ' (' ||
        (SELECT DS_Organo FROM AUX_Organos_PROD
         WHERE OG.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
          AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,

	(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_Organos_PROD
			 WHERE OG1.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
        AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN OG1.DS_Organo ||
					NVL((SELECT ' / ' || MAX(UPPER(Nombre)) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE OG1.DS_Organo || ' (' ||
        (SELECT DS_Organo FROM AUX_Organos_PROD
					WHERE OG1.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
          AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(UPPER(Nombre)) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(UPPER(Nombre)) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,

	CASE WHEN (CD_Org_Destino IS NULL AND CD_Dep_Destino IS NULL) OR (CD_Org_Destino IS NOT NULL)
	THEN
	(SELECT MAX(LOCALIDAD) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS LOCALIDAD,

	CASE WHEN (CD_Org_Destino IS NULL AND CD_Dep_Destino IS NULL) OR (CD_Org_Destino IS NOT NULL)
	THEN
	(SELECT MAX(PAIS) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS PAIS,

	CASE WHEN (CD_Org_Destino IS NULL AND CD_Dep_Destino IS NULL) OR (CD_Org_Destino IS NOT NULL)
	THEN
		(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	END AS PROVINCIA,

	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
  DECODE(ES.Tipo_Regis_Original,'E',' / Entrada','S',' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	OFI.Direccion,
	ES.Num_Transporte,
	NVL(CM.CONTENIDO,' ') || ' ' || ES.Resumen AS Resumen,
	ES.CD_Org_Destino,
	(select c.DS_Logo
   from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = u)
   as Organismo,
   CASE WHEN v_ta IS NOT NULL
	   THEN CM.DS_COMENTARIO
	   ELSE ''
	END AS TipoAsunto


FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1,
  AUX_TRANSPORTES TR, UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, AUX_COMENTARIOS CM
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Div_Origen = OG.CD_Division)
	AND (ES.CD_Org_Origen = OG.CD_Organo)
	AND (ES.CD_Org_Origen = DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen = DEP.CD_Departamento(+))
	AND (ES.CD_Org_Destino = DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino = DEP1.CD_Departamento(+))
	AND (ES.CD_Div_Destino = OG1.CD_Division(+))
	AND (UPPER(ES.CD_Org_Destino) = UPPER(OG1.CD_Organo(+)))
	AND (ES.Tipo_Transporte =TR.CD_Transporte(+))
	AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
	AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'S'
  --AND (TRUNC(ES.Fecha_presen) >= fv and trunc(ES.Fecha_presen) <= ff)
  AND (ES.Fecha_presen_sh >= fv and ES.Fecha_presen_sh <= ff)
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	-- AND (ES.Num_RegistroOF >= s OR s is Null)
	-- AND (ES.Num_RegistroOF <= t OR t is Null)
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND UUU.CD_USUARIO = u
	AND ES.Estado IS NULL
	AND ES.CD_Org_Destino IS NOT NULL
  AND ( (ES.CD_Org_Destino  = w OR w is null) OR
        ((ES.CD_Org_Destino IN
        	 (SELECT CD_Organo FROM AUX_ORGANOS_PROD WHERE CD_Oficina =
           	  (SELECT CD_Oficina FROM AUX_ORGANOS_PROD WHERE CD_Organo = w and CD_Division = v)) OR w is null)))
   AND (ES.CD_DIV_DESTINO = v OR v IS NULL OR v = 0 OR v = '')
  ----
  --AND (ES.CD_DIV_DESTINO = v OR v IS NULL)
  AND ((v_ta IS NOT NULL AND TO_CHAR(ES.CD_TIPOASUNTO_COM) = v_ta) OR
               (v_ta is null))

ORDER BY
  OrgDestino, Num_RegistroOF;

cursorOut:=ret_cursor;

END usp_RPT_IndicesSD_FPre_2200;

/*************************************************************************************************/
PROCEDURE usp_RPT_IndicesSD_fechas_MIN(p IN VARCHAR2,
--fv in date, ff in date,
kk IN VARCHAR2, cc IN VARCHAR2,
s IN INTEGER,t IN INTEGER , u IN VARCHAR2, cursorOut OUT t_cursor)
IS

ret_cursor t_cursor;
fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

BEGIN

OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
  (CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
	 END) AS NumRegistro,
	ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha ,
	DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
  (CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN DEP.DS_Departamento || '.' || OG.DS_Organo
			ELSE DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_Organos_PROD
			 WHERE OG.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
        AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN OG.DS_Organo
				ELSE OG.DS_Organo || ' (' ||
        (SELECT DS_Organo FROM AUX_Organos_PROD
         WHERE OG.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
          AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,

	(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
      (SELECT DS_Organo FROM AUX_Organos_PROD
			 WHERE OG1.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
        AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN OG1.DS_Organo ||
					NVL((SELECT ' / ' || MAX(UPPER(Nombre)) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE OG1.DS_Organo || ' (' ||
        (SELECT DS_Organo FROM AUX_Organos_PROD
					WHERE OG1.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
          AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(UPPER(Nombre)) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(UPPER(Nombre)) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,

	CASE WHEN (CD_Org_Destino IS NULL AND CD_Dep_Destino IS NULL) OR (CD_Org_Destino IS NOT NULL)
	THEN
	(SELECT MAX(LOCALIDAD) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS LOCALIDAD,

	CASE WHEN (CD_Org_Destino IS NULL AND CD_Dep_Destino IS NULL) OR (CD_Org_Destino IS NOT NULL)
	THEN
	(SELECT MAX(PAIS) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS PAIS,

	CASE WHEN (CD_Org_Destino IS NULL AND CD_Dep_Destino IS NULL) OR (CD_Org_Destino IS NOT NULL)
	THEN
		(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	END AS PROVINCIA,

	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
  DECODE(ES.Tipo_Regis_Original,'E',' / Entrada','S',' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	OFI.Direccion,
	ES.Num_Transporte,
	ES.Resumen,
	ES.CD_Org_Destino,
	(select c.DS_Logo
   from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = u)
   as Organismo
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1,
  AUX_TRANSPORTES TR, UsuariosV1 US, DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Div_Origen = OG.CD_Division)
	AND (ES.CD_Org_Origen = OG.CD_Organo)
	AND (ES.CD_Org_Origen = DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen = DEP.CD_Departamento(+))
	AND (ES.CD_Org_Destino = DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino = DEP1.CD_Departamento(+))
	AND (ES.CD_Div_Destino = OG1.CD_Division(+))
	AND (UPPER(ES.CD_Org_Destino) = UPPER(OG1.CD_Organo(+)))
	AND (ES.Tipo_Transporte =TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'S'
	AND (ES.Fecha  BETWEEN fv AND ff)
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	-- AND (ES.Num_RegistroOF >= s OR s is Null)
	-- AND (ES.Num_RegistroOF <= t OR t is Null)
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND UUU.CD_USUARIO = u
	AND ES.Estado IS NULL
	AND ES.CD_Org_Destino IS NOT NULL

ORDER BY
  OrgDestino, Num_RegistroOF;

cursorOut:=ret_cursor;

END usp_RPT_IndicesSD_fechas_MIN;


/*************************************************************************************************/
procedure usp_RPT_IndicesSDI_fechas(p         IN VARCHAR2,
                                    kk        IN VARCHAR2,
                                    cc        IN VARCHAR2,
                                    s         IN INTEGER,
                                    t         IN INTEGER,
                                    x         IN VARCHAR2,
                                    cursorOut OUT t_cursor) IS

  ret_cursor t_cursor;
  fv         DATE := TO_DATE(kk, 'DD/MM/YYYY');
  ff         DATE := TO_DATE(cc, 'DD/MM/YYYY');

BEGIN

  OPEN ret_cursor FOR

    SELECT DISTINCT fv AS fechai,
                    ff AS fechaf,
                    ES.Num_RegistroOF,
                    (CASE
                      WHEN (SELECT Num_RG
                              FROM REGISTROGENERAL RG
                             WHERE RG.Ejercicio = ES.Ejercicio
                               AND RG.TipoES = ES.TipoES
                               AND RG.CD_Oficina = ES.CD_Oficina
                               AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL THEN
                       --TO_CHAR(ES.Num_RegistroOF) || '/RG ' ||
                       TO_CHAR(ES.Num_RegistroOF) || NVL2(OFI.SIGLAOF,' (' || OFI.SIGLAOF || ') ','') || '/RG ' ||
                       TO_CHAR((SELECT Num_RG
                                 FROM REGISTROGENERAL RG
                                WHERE RG.Ejercicio = ES.Ejercicio
                                  AND RG.TipoES = ES.TipoES
                                  AND RG.CD_Oficina = ES.CD_Oficina
                                  AND RG.Num_Registro = ES.Num_RegistroOF))
                      ELSE
                       --TO_CHAR(ES.Num_RegistroOF)
                       TO_CHAR(ES.Num_RegistroOF) || NVL2(OFI.SIGLAOF,' (' || OFI.SIGLAOF || ') ','')
                    END) AS NumRegistro,
                    ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
                    ES.FECHA_REGIS_ORIGINAL AS Fecharem,
                    OFI.DS_Oficina,
                    ES.FechaP AS Fecha,
                    DECODE(ES.TipoES, 'E', 'Entrada', 'S', 'Salida', '') AS TipoES,
                    US.DS_Usuario,
                    (CASE
                      WHEN CD_Organo_Origen IS NOT NULL AND
                           CD_Dep_Origen IS NOT NULL THEN
                       CASE
                      WHEN OG.CD_Organosup IS NULL THEN
                       DEP.DS_Departamento || '.' || OG.DS_Organo
                      ELSE
                       DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
                       (SELECT AUX_OrganosV1.DS_Organo
                          FROM AUX_OrganosV1
                         WHERE OG.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')'
                    END ELSE CASE
                       WHEN CD_Organo_Origen IS NOT NULL THEN
                        CASE
                       WHEN OG.CD_Organosup IS NULL THEN
                        OG.DS_Organo

                       ELSE
                        OG.DS_Organo || ' (' ||
                        (SELECT AUX_OrganosV1.DS_Organo
                           FROM AUX_OrganosV1
                          WHERE OG.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')'
                     END ELSE (SELECT MAX(Nombre)
                                 FROM REL_INTVSRES RINT
                                WHERE RINT.Ejercicio = ES.Ejercicio
                                  AND RINT.TipoES = ES.TipoES
                                  AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio,
                                         RINT.TipoES,
                                         RINT.Num_Registro) END END) AS OrgOrigen,
                    (CASE
                      WHEN CD_Organo_Destino IS NOT NULL AND
                           CD_Dep_Destino IS NOT NULL THEN
                       CASE
                      WHEN OG1.CD_Organosup IS NULL THEN
                       DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
                       NVL((SELECT ' / ' || MAX(Nombre)
                             FROM REL_INTVSRES RINT
                            WHERE RINT.Ejercicio = ES.Ejercicio
                              AND RINT.TipoES = ES.TipoES
                              AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio,
                                     RINT.TipoES,
                                     RINT.Num_Registro),
                           ' ')
                      ELSE
                       DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
                       (SELECT AUX_OrganosV1.DS_Organo
                          FROM AUX_OrganosV1
                         WHERE OG1.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')' ||
                       NVL((SELECT ' / ' || MAX(Nombre)
                             FROM REL_INTVSRES RINT
                            WHERE RINT.Ejercicio = ES.Ejercicio
                              AND RINT.TipoES = ES.TipoES
                              AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio,
                                     RINT.TipoES,
                                     RINT.Num_Registro),
                           ' ')
                    END ELSE CASE
                       WHEN CD_Organo_Destino IS NOT NULL THEN
                        CASE
                       WHEN OG1.CD_Organosup IS NULL THEN
                        OG1.DS_Organo ||
                        NVL((SELECT ' / ' || MAX(Nombre)
                              FROM REL_INTVSRES RINT
                             WHERE RINT.Ejercicio = ES.Ejercicio
                               AND RINT.TipoES = ES.TipoES
                               AND RINT.Num_Registro = ES.Num_Registro
                             GROUP BY RINT.Ejercicio,
                                      RINT.TipoES,
                                      RINT.Num_Registro),
                            ' ')
                       ELSE
                        OG1.DS_Organo || ' (' ||
                        (SELECT AUX_OrganosV1.DS_Organo
                           FROM AUX_OrganosV1
                          WHERE OG1.CD_Organosup = AUX_OrganosV1.CD_Organo(+)) || ')' ||
                        NVL((SELECT ' / ' || MAX(Nombre)
                              FROM REL_INTVSRES RINT
                             WHERE RINT.Ejercicio = ES.Ejercicio
                               AND RINT.TipoES = ES.TipoES
                               AND RINT.Num_Registro = ES.Num_Registro
                             GROUP BY RINT.Ejercicio,
                                      RINT.TipoES,
                                      RINT.Num_Registro),
                            ' ')
                     END ELSE (SELECT MAX(Nombre)
                                 FROM REL_INTVSRES RINT
                                WHERE RINT.Ejercicio = ES.Ejercicio
                                  AND RINT.TipoES = ES.TipoES
                                  AND RINT.Num_Registro = ES.Num_Registro
                                GROUP BY RINT.Ejercicio,
                                         RINT.TipoES,
                                         RINT.Num_Registro) END END) AS OrgDestino,
                    CASE
                      WHEN (CD_Organo_Origen IS NULL AND
                           CD_Dep_Origen IS NULL) OR
                           (CD_Organo_Origen IS NOT NULL) THEN
                       (SELECT MAX(LOCALIDAD)
                          FROM REL_INTVSRES RINT
                         WHERE RINT.Ejercicio = ES.Ejercicio
                           AND RINT.TipoES = ES.TipoES
                           AND RINT.Num_Registro = ES.Num_Registro
                         GROUP BY RINT.Ejercicio,
                                  RINT.TipoES,
                                  RINT.Num_Registro)
                    END AS LOCALIDAD,
                    CASE
                      WHEN (CD_Organo_Origen IS NULL AND
                           CD_Dep_Origen IS NULL) OR
                           (CD_Organo_Origen IS NOT NULL) THEN
                       (SELECT MAX(PAIS)
                          FROM REL_INTVSRES RINT
                         WHERE RINT.Ejercicio = ES.Ejercicio
                           AND RINT.TipoES = ES.TipoES
                           AND RINT.Num_Registro = ES.Num_Registro
                         GROUP BY RINT.Ejercicio,
                                  RINT.TipoES,
                                  RINT.Num_Registro)
                    END AS PAIS,
                    CASE
                      WHEN (CD_Organo_Origen IS NULL AND
                           CD_Dep_Origen IS NULL) OR
                           (CD_Organo_Origen IS NOT NULL) THEN
                       (SELECT DS_PROVINCIA
                          FROM AUX_PROVINCIAS
                         WHERE CD_PROVINCIA =
                               (SELECT MAX(PROVINCIA)
                                  FROM REL_INTVSRES RINT
                                 WHERE RINT.Ejercicio = ES.Ejercicio
                                   AND RINT.TipoES = ES.TipoES
                                   AND RINT.Num_Registro = ES.Num_Registro
                                 GROUP BY RINT.Ejercicio,
                                          RINT.TipoES,
                                          RINT.Num_Registro))
                    END AS PROVINCIA,
                    ' /' || ES.CD_Regis_Original AS CD_Regis_Original,
                    DECODE(ES.Tipo_Regis_Original,
                           'E',
                           ' / Entrada',
                           'S',
                           ' / Salida',
                           '') AS Tipo_Regis_original,
                    ES.Num_Regis_Original,

                    ES.Fecha_Regis_Original,
                    ES.Tipo_Transporte,
                    TR.DS_Transporte,
                    OFI.Direccion,
                    ES.Num_Transporte,
                    ES.Resumen,
                    ES.CD_Organo_Destino,
                    (select c.DS_Logo
                       from Dat_Config c
                      inner join USUARIOS uu on uu.CD_Organismo =
                                                c.CD_Organismo
                                            and uu.CD_Usuario = x) as Organismo

      FROM AUX_OFICINAS      OFI,
           REGISTROES        ES,
           AUX_ORGANOS       OG,
           AUX_ORGANOS       OG1,
           AUX_TRANSPORTES   TR,
           UsuariosV1        US,
           AUX_DEPARTAMENTOS DEP,
           AUX_DEPARTAMENTOS DEP1
     WHERE ES.CD_Oficina = OFI.CD_Oficina
       AND (ES.CD_Division_Origen = OG.CD_Division)
       AND (ES.CD_Organo_Origen = OG.CD_Organo)
       AND (ES.CD_Organo_Origen = DEP.CD_Organo(+))
       AND (ES.CD_Dep_Origen = DEP.CD_Departamento(+))
       AND (ES.CD_Organo_Destino = DEP1.CD_Organo(+))
       AND (ES.CD_Dep_Destino = DEP1.CD_Departamento(+))
       AND (ES.CD_Division_Destino = OG1.CD_Division(+))
       AND (ES.CD_Organo_Destino = OG1.CD_Organo(+))
       AND (ES.Tipo_Transporte = TR.CD_Transporte(+))
       AND ES.Usuario = US.DNI
       AND OFI.CD_Oficina = p
       AND ES.TipoES = 'S'
       AND (ES.Fecha BETWEEN fv AND ff)
       AND (fv IS NOT NULL OR ff IS NOT NULL)
       AND (ES.Num_RegistroOF >= s OR s = 0)
       AND (ES.Num_RegistroOF <= t OR t = 0)
       AND ES.Estado IS NULL
       AND ES.CD_Organo_Destino IS NULL

     ORDER BY OrgOrigen, Num_RegistroOF;

  cursorOut := ret_cursor;
END usp_RPT_IndicesSDI_fechas;


/*************************************************************************************************/
-- 24/03/2006  para IGAE-INE
-- se añade un parametro (u)  y el acceso a dat_config y usuarios)
PROCEDURE usp_RPT_IndicesSDI_fechas_2(p IN VARCHAR2,
--fv in date,ff in date,
kk IN VARCHAR2, cc IN VARCHAR2,
s IN INTEGER, t IN INTEGER, u IN VARCHAR2,  cursorOut OUT t_cursor) IS

  ret_cursor t_cursor;
fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

 BEGIN

  OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
		(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha ,
	DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
    (CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN CD_Organo_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo

				ELSE  OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Organo_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG1.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Organo_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG1.DS_Organo || ' (' || (SELECT AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG1.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	CASE WHEN (CD_Organo_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
	(SELECT MAX(LOCALIDAD)  FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS LOCALIDAD,
	CASE WHEN  (CD_Organo_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
	(SELECT MAX(PAIS)  FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS PAIS,
	CASE WHEN  (CD_Organo_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
		(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	END AS PROVINCIA,
	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
    DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,

	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	OFI.Direccion,
	ES.Num_Transporte,
	ES.Resumen,
	ES.CD_Organo_Destino,
	(select c.DS_Logo
  from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and
    uu.CD_Usuario = u)
   as Organismo

FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS OG,
	 AUX_ORGANOS OG1,AUX_TRANSPORTES TR, UsuariosV1 US,
	DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Division_Origen = OG.CD_Division)
	AND (ES.CD_Organo_Origen = OG.CD_Organo)
	AND (ES.CD_Organo_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_Organo_Destino =DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+))
	AND (ES.CD_Division_Destino=OG1.CD_Division(+))
	AND (ES.CD_Organo_Destino =OG1.CD_Organo(+))
	AND (ES.Tipo_Transporte =TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'S'
	AND (ES.Fecha  BETWEEN fv AND ff)
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	-- AND (ES.Num_RegistroOF >= s OR s is Null)
	-- AND (ES.Num_RegistroOF <= t OR t is Null)
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND UUU.CD_USUARIO = u
	AND ES.Estado IS NULL
	AND ES.CD_Organo_Destino IS  NULL


ORDER BY  OrgOrigen,Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_IndicesSDI_fechas_2;


/*************************************************************************************************/
PROCEDURE usp_RPT_LibroRegistro(p IN INTEGER,
q IN VARCHAR2,fv IN DATE, ff IN DATE,  cursorOut OUT t_cursor)
IS
  ret_cursor t_cursor;

  --fv  DATE := TO_DATE(r,'YYYY-MM-DD');
  -- ff  DATE := TO_DATE(s,'YYYY-MM-DD');

BEGIN
  OPEN ret_cursor FOR

SELECT
	fv AS fechai, ff AS fechaf,
	ES.Ejercicio,
	RG.Num_RG,
	ES.CD_Oficina ,
	OFI.DS_Oficina,
	ES.Num_RegistroOF,
	ES.Fecha ,
	DECODE(ES.TipoES,'E', 'Entrada', 'S', 'Salida','') AS TipoES,
	ES.Usuario ,
	US.DS_Usuario,
	ES.CD_Organo_Destino,
	ES.CD_Organo_Origen,
    (CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			END
	ELSE
		CASE WHEN CD_Organo_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				ELSE  OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Organo_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
				CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG1.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			END
	ELSE
		CASE WHEN CD_Organo_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo ||
					CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				ELSE  OG1.DS_Organo || ' (' || (SELECT AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG1.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	ES.CD_Regis_Original,
		DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	ES.Num_Transporte,
	ES.Resumen
FROM
	AUX_OFICINAS OFI, REGISTROGENERAL RG, REGISTROES ES, AUX_ORGANOS OG,  AUX_ORGANOS OG1,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, AUX_TRANSPORTES TR, USUARIOS US
WHERE
	ES.Ejercicio = RG.Ejercicio
	AND ES.TipoES = RG.TipoES
	AND ES.CD_Oficina = RG.CD_Oficina
	AND ES.Num_RegistroOF = RG.Num_Registro
	AND ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Organo_Origen=OG.CD_Organo(+))
	AND (ES.CD_Organo_Destino=OG1.CD_Organo(+))
	AND (ES.CD_Organo_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_Organo_Destino=DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+))
	AND (ES.Tipo_Transporte =TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND ES.Ejercicio = p
	AND ES.TipoES= q
	AND (p IS NOT NULL OR q IS NOT NULL)
	AND (ES.Fecha >= fv OR fv is null)
	AND (ES.Fecha <= ff OR ff Is NULL)


ORDER BY  Num_RG;

  cursorOut:=ret_cursor;
END usp_RPT_LibroRegistro;




/*************************************************************************************************/
--27/03/2006   NUevo para INE e IGAE con logo
PROCEDURE usp_RPT_LibroRegistro_2(p IN INTEGER,
q IN VARCHAR2,fv IN DATE, ff IN DATE,  ú IN VARCHAR2, cursorOut OUT t_cursor)
IS
  ret_cursor t_cursor;

  --fv  DATE := TO_DATE(r,'YYYY-MM-DD');
  -- ff  DATE := TO_DATE(s,'YYYY-MM-DD');

BEGIN
  OPEN ret_cursor FOR

SELECT
	fv AS fechai, ff AS fechaf,
	ES.Ejercicio,
	RG.Num_RG,
	ES.CD_Oficina ,
	OFI.DS_Oficina,
	ES.Num_RegistroOF,
	ES.Fecha ,
	DECODE(ES.TipoES,'E', 'Entrada', 'S', 'Salida','') AS TipoES,
	ES.Usuario ,
	US.DS_Usuario,
	ES.CD_Organo_Destino,
	ES.CD_Organo_Origen,
    (CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			END
	ELSE
		CASE WHEN CD_Organo_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				ELSE  OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Organo_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
				CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG1.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			END
	ELSE
		CASE WHEN CD_Organo_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo ||
					CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				ELSE  OG1.DS_Organo || ' (' || (SELECT AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG1.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	ES.CD_Regis_Original,
		DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	ES.Num_Transporte,
	ES.Resumen,
		(select c.DS_Logo
  from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and
    uu.CD_Usuario = ú)
   as Organismo
FROM
	AUX_OFICINAS OFI, REGISTROGENERAL RG, REGISTROES ES, AUX_ORGANOS OG,  AUX_ORGANOS OG1,
	DAT_CONFIG CC,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, AUX_TRANSPORTES TR, USUARIOS US
WHERE
	ES.Ejercicio = RG.Ejercicio
	AND ES.TipoES = RG.TipoES
	AND ES.CD_Oficina = RG.CD_Oficina
	AND ES.CD_Oficina = US.CD_Oficina
	AND ES.Num_RegistroOF = RG.Num_Registro
	AND ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Organo_Origen=OG.CD_Organo(+))
	AND (ES.CD_Organo_Destino=OG1.CD_Organo(+))
	AND (ES.CD_Organo_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_Organo_Destino=DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+))
	AND (ES.Tipo_Transporte =TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND US.CD_USUARIO = ú
	AND US.CD_ORGANISMO = CC.CD_ORGANISMO
	AND US.CD_USUARIO = ú
	AND ES.Ejercicio = p
	AND ES.TipoES= q
	AND (p IS NOT NULL OR q IS NOT NULL)
	AND (ES.Fecha >= fv OR fv is null)
	AND (ES.Fecha <= ff OR ff Is NULL)


ORDER BY  Num_RG;

  cursorOut:=ret_cursor;
END usp_RPT_LibroRegistro_2;



/*************************************************************************************************/
PROCEDURE usp_RPT_LibroRegistro_MIN(p IN INTEGER, q IN VARCHAR2,
          fv IN DATE, ff IN DATE, ú IN VARCHAR2, cursorOut OUT t_cursor)
IS
  ret_cursor t_cursor;

BEGIN
  OPEN ret_cursor FOR

    SELECT
    	fv AS fechai, ff AS fechaf,
    	ES.Ejercicio,
    	RG.Num_RG,
    	ES.CD_Oficina ,
    	OFI.DS_Oficina,
    	ES.Num_RegistroOF,
    	ES.Fecha ,
    	DECODE(ES.TipoES,'E', 'Entrada', 'S', 'Salida','') AS TipoES,
    	ES.Usuario ,
    	US.DS_Usuario,
    	ES.CD_Org_Destino,
    	ES.CD_Org_Origen,
      (CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
    		THEN CASE WHEN OG.CD_Organosup IS NULL
    			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
    				CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    				ELSE ' '
    				END
    			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
           (SELECT DS_Organo FROM AUX_Organos_Prod WHERE
    				OG.CD_Organosup = AUX_Organos_Prod.CD_Organo(+) AND OG.CD_Division = AUX_Organos_Prod.CD_Division(+)) || ')' ||
    				CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    				ELSE ' '
    				END
    			END
    	ELSE
    		CASE WHEN ES.CD_Org_Origen IS NOT NULL
    			THEN CASE WHEN OG.CD_Organosup IS NULL
    				THEN	OG.DS_Organo ||
    					CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    					ELSE ' '
    					END
    				ELSE OG.DS_Organo || ' (' ||
             (SELECT DS_Organo FROM AUX_Organos_Prod WHERE
    					OG.CD_Organosup = AUX_Organos_Prod.CD_Organo(+) AND OG.CD_Division = AUX_Organos_Prod.CD_Division(+)) || ')' ||
    					CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    					ELSE ' '
    					END
    				END
    			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
    		END
    	END) AS OrgOrigen,
    	(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
    		THEN CASE WHEN OG1.CD_Organosup IS NULL
    			THEN DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
    				CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    				ELSE ' '
    				END
    			ELSE DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
           (SELECT DS_Organo FROM AUX_Organos_Prod WHERE
    				OG1.CD_Organosup = AUX_Organos_Prod.CD_Organo(+) AND OG1.CD_Division = AUX_Organos_Prod.CD_Division(+)) || ')' ||
    				CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    				ELSE ' '
    				END
    			END
    	ELSE
    		CASE WHEN CD_Org_Destino IS NOT NULL
    			THEN CASE WHEN OG1.CD_Organosup IS NULL
    				THEN OG1.DS_Organo ||
    					CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    					ELSE ' '
    					END
    				ELSE OG1.DS_Organo || ' (' ||
             (SELECT DS_Organo FROM AUX_Organos_Prod WHERE
    					OG1.CD_Organosup = AUX_Organos_Prod.CD_Organo(+) AND OG1.CD_Division = AUX_Organos_Prod.CD_Division(+)) || ')' ||
    					CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    					ELSE ' '
    					END
    				END
    			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
    		END
      END) AS OrgDestino,
    	ES.CD_Regis_Original,
    	DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
    	ES.Num_Regis_Original,
    	ES.Fecha_Regis_Original,
    	ES.Tipo_Transporte,
    	TR.DS_Transporte,
    	ES.Num_Transporte,
    	ES.Resumen,
    	(select c.DS_Logo
       from Dat_Config c inner join USUARIOS uu on
        uu.CD_Organismo = c.CD_Organismo and
        uu.CD_Usuario = ú)
       as Organismo
    FROM
    	AUX_OFICINAS OFI, REGISTROGENERAL RG, REGISTROES ES, AUX_ORGANOS_PROD OG,
      AUX_ORGANOS_PROD OG1,	DAT_CONFIG CC,
    	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, AUX_TRANSPORTES TR, USUARIOS US
    WHERE
    	ES.Ejercicio = RG.Ejercicio
    	AND ES.TipoES = RG.TipoES
    	AND ES.CD_Oficina = RG.CD_Oficina
    	AND ES.CD_Oficina = US.CD_Oficina
    	AND ES.Num_RegistroOF = RG.Num_Registro
    	AND ES.CD_Oficina = OFI.CD_Oficina
    	AND (ES.CD_Div_Origen = OG.CD_Division(+))
    	AND (ES.CD_Org_Origen = OG.CD_Organo(+))
    	AND (ES.CD_Div_Destino = OG1.CD_Division(+))
    	AND (ES.CD_Org_Destino = OG1.CD_Organo(+))
    	AND (ES.CD_Org_Origen = DEP.CD_Organo(+))
    	AND (ES.CD_Dep_Origen = DEP.CD_Departamento(+))
    	AND (ES.CD_Org_Destino = DEP1.CD_Organo(+))
    	AND (ES.CD_Dep_Destino = DEP1.CD_Departamento(+))
    	AND (ES.Tipo_Transporte = TR.CD_Transporte(+))
    	AND ES.Usuario = US.DNI
    	AND US.CD_USUARIO = ú
    	AND US.CD_ORGANISMO = CC.CD_ORGANISMO
    	AND US.CD_USUARIO = ú
    	AND ES.Ejercicio = p
    	AND ES.TipoES= q
    	AND (p IS NOT NULL OR q IS NOT NULL)
    	AND (ES.Fecha >= fv OR fv is null)
    	AND (ES.Fecha <= ff OR ff Is NULL)
    ORDER BY  Num_RG;

  cursorOut:=ret_cursor;
END usp_RPT_LibroRegistro_MIN;


/*************************************************************************************************/
PROCEDURE usp_RPT_LibroRegistro_fechas(p IN INTEGER,q IN VARCHAR2,fv IN DATE,
                                       ff IN DATE, cursorOut OUT t_cursor) IS
  ret_cursor t_cursor;

BEGIN
  OPEN ret_cursor FOR

SELECT
    fv AS fechai, ff AS fechaf,
	ES.Ejercicio,
	RG.Num_RG,
	ES.CD_Oficina ,
	OFI.DS_Oficina,
	ES.Num_RegistroOF,
	ES.Fecha ,
	DECODE(ES.TipoES,'E', 'Entrada', 'S', 'Salida','') AS TipoES,
	ES.Usuario ,
	US.DS_Usuario,
	ES.CD_Organo_Destino,
	ES.CD_Organo_Origen,
	(CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			END
	ELSE
		CASE WHEN CD_Organo_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				ELSE  OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				 RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Organo_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
				CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG1.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			END
	ELSE
		CASE WHEN CD_Organo_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo ||
					CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				ELSE  OG1.DS_Organo || ' (' || (SELECT AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG1.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	ES.CD_Regis_Original,
	DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	ES.Num_Transporte,
	ES.Resumen
FROM
	AUX_OFICINAS OFI, REGISTROGENERAL RG, REGISTROES ES, AUX_ORGANOS OG,  AUX_ORGANOS OG1,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, AUX_TRANSPORTES TR, USUARIOS US
WHERE
	ES.Ejercicio = RG.Ejercicio
	AND ES.TipoES = RG.TipoES
	AND ES.CD_Oficina = RG.CD_Oficina
	AND ES.Num_RegistroOF = RG.Num_Registro
	AND ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Organo_Origen=OG.CD_Organo(+))
	AND (ES.CD_Organo_Destino=OG1.CD_Organo(+))
	AND (ES.CD_Organo_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_Organo_Destino=DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+))
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND ES.TipoES= q
	AND ES.Ejercicio = p
	AND ((ES.Fecha BETWEEN fv AND ff) OR (fv IS NULL AND ff IS NULL))
	AND (p IS NOT NULL OR q IS NOT NULL)

ORDER BY  Num_RG;

  cursorOut:=ret_cursor;
END usp_RPT_LibroRegistro_fechas;

/*************************************************************************************************/
PROCEDURE usp_RPT_Organos (P_Division IN INTEGER,
                           P_Organo   IN VARCHAR2,
                           P_Descrip  IN VARCHAR2,
                           cursorOut OUT t_cursor) IS
  ret_cursor t_cursor;
  v_Division AUX_DIVISIONES.CD_Division%TYPE:= P_Division;
  v_Organo AUX_ORGANOS_PROD.CD_ORGANO%TYPE:= upper(P_Organo);
  v_Descrip AUX_ORGANOS_PROD.DS_Organo%TYPE:= upper(P_Descrip);
  v_cadena VARCHAR2(2000);

BEGIN
  v_cadena :=
  'SELECT ORG.CD_Division,
          ORG.CD_Organo,
          ORG.DS_Organo,
          ORG.CD_OrganoSup,
          ORG2.DS_Organo as DS_OrganoSup
   FROM AUX_ORGANOS_PROD ORG
        LEFT JOIN AUX_ORGANOS_PROD ORG2 ON
             ORG2.CD_DIVISION = ORG.CD_DIVISIONSUP
             AND ORG2.CD_ORGANO = ORG.CD_ORGANOSUP
   WHERE ORG.CD_Division = ' || v_Division ;

  IF P_Organo IS NOT NULL THEN
     v_cadena := v_cadena || ' AND (upper(ORG.CD_Organo) LIKE ''%'' || NVL(''' || v_Organo || ''',''%'') || ''%'')';
  END IF;

  IF P_Descrip IS NOT NULL THEN
     v_cadena := v_cadena || ' AND (upper(ORG.DS_Organo) LIKE ''%'' || NVL(''' || v_Descrip || ''',''%'') || ''%'')';
  END IF;

  v_cadena := v_cadena || ' ORDER BY ORG.CD_Division, ORG.CD_Organo';

  OPEN cursorOut FOR v_cadena;

END usp_RPT_Organos;

/*************************************************************************************************/
PROCEDURE usp_RPT_Pendientes1(p IN VARCHAR2, cursorOut OUT t_cursor) IS
  ret_cursor t_cursor;

BEGIN
  OPEN ret_cursor FOR

SELECT DISTINCT
	ES.Num_RegistroOF AS NumRegistro,
	OFI.DS_Oficina,
	ES.Fecha ,
    (CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Organo_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Organo_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG1.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN CD_Organo_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo
				ELSE  OG1.DS_Organo || ' (' || (SELECT AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG1.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	EX.DS_Expediente AS Asunto
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS OG,  AUX_ORGANOS OG1,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, DAT_EXPEDIENTES EX
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Division_Origen=OG.CD_Division(+))
	AND (ES.CD_Organo_Origen=OG.CD_Organo(+))
	AND (ES.CD_Organo_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_Organo_Destino=DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+))
	AND (ES.CD_Division_Destino = OG1.CD_Division)
	AND (ES.CD_Organo_Destino = OG1.CD_Organo)
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND ES.CD_EjercicioExp = EX.CD_EjercicioExp
	AND ES.CD_Expediente = EX.CD_Expediente
	AND EX.F_Cierre IS NULL
	AND EX.DS_Expediente LIKE '%(24.2)%'
	AND ES.CD_Tipo = 1

ORDER BY  ES.Fecha, NumRegistro;

  cursorOut:=ret_cursor;
END usp_RPT_Pendientes1;


/*************************************************************************************************/
PROCEDURE usp_RPT_Pendientes2(p VARCHAR2 , cursorOut OUT t_cursor) IS
  ret_cursor t_cursor;

BEGIN
  OPEN ret_cursor FOR

SELECT DISTINCT
	ES.Num_RegistroOF AS NumRegistro,
	OFI.DS_Oficina,
	ES.Fecha ,
    (CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Organo_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Organo_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG1.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN CD_Organo_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo
				ELSE  OG1.DS_Organo || ' (' || (SELECT AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG1.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	EX.DS_Expediente AS Asunto
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS OG,  AUX_ORGANOS OG1,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, DAT_EXPEDIENTES EX
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Division_Origen=OG.CD_Division(+))
	AND (ES.CD_Organo_Origen=OG.CD_Organo(+))
	AND (ES.CD_Organo_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_Organo_Destino=DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+))
	AND (ES.CD_Division_Destino = OG1.CD_Division)
	AND (ES.CD_Organo_Destino = OG1.CD_Organo)
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND ES.CD_EjercicioExp = EX.CD_EjercicioExp
	AND ES.CD_Expediente = EX.CD_Expediente
	AND EX.F_Cierre IS NULL
	AND EX.DS_Expediente LIKE '%(22.2)%'
	AND ES.CD_Tipo = 1

ORDER BY  ES.Fecha, NumRegistro;

  cursorOut:=ret_cursor;
END usp_RPT_Pendientes2;



/*************************************************************************************************/
PROCEDURE usp_RPT_Pendientes3(p IN VARCHAR2, cursorOut OUT t_cursor) IS
  ret_cursor t_cursor;

BEGIN
  OPEN ret_cursor FOR

SELECT DISTINCT
	ES.Num_RegistroOF AS NumRegistro,
	OFI.DS_Oficina,
	ES.Fecha ,
	(CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Organo_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Organo_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG1.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN CD_Organo_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo
				ELSE  OG1.DS_Organo || ' (' || (SELECT AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG1.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	EX.DS_Expediente AS Asunto
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS OG,  AUX_ORGANOS OG1,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, DAT_EXPEDIENTES EX
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Division_Origen=OG.CD_Division(+))
	AND (ES.CD_Organo_Origen=OG.CD_Organo(+))
	AND (ES.CD_Organo_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_Organo_Destino=DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+))
	AND (ES.CD_Division_Destino = OG1.CD_Division)
	AND (ES.CD_Organo_Destino = OG1.CD_Organo)
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'E'
	AND ES.CD_EjercicioExp = EX.CD_EjercicioExp
	AND ES.CD_Expediente = EX.CD_Expediente
	AND EX.F_Cierre IS NULL
	AND EX.DS_Expediente LIKE '%(INF)%'
	AND ES.CD_Tipo = 1

ORDER BY  ES.Fecha, NumRegistro;

  cursorOut:=ret_cursor;
END usp_RPT_Pendientes3;



/*************************************************************************************************/
PROCEDURE usp_RPT_Registro(p IN  VARCHAR2,q IN VARCHAR2,
r IN INTEGER, s IN INTEGER, t IN INTEGER, cursorOut OUT t_cursor) IS
  ret_cursor t_cursor;

  fv  DATE := TO_DATE(s, 'DD');
  ff  DATE := TO_DATE(t, 'DD');

/*
if @r='' select @r =null
declare @fv as datetime
declare @ff as datetime
SET dateformat ymd
select @fv=dateadd(day,@s,'1899-12-30')
select @ff=dateadd(day,@t,'1899-12-30')
*/

BEGIN
  OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai, ff AS fechaf,
	ES.Num_RegistroOF,
    (CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha ,
    DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
    (CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			END
	ELSE
		CASE WHEN CD_Organo_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				ELSE  OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Organo_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
				CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG1.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			END
	ELSE
		CASE WHEN CD_Organo_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo ||
					CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				ELSE  OG1.DS_Organo || ' (' || (SELECT AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG1.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END )AS OrgDestino,
	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
	DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	ES.Num_Transporte,
	ES.Resumen
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS OG,  AUX_ORGANOS OG1,AUX_TRANSPORTES TR, UsuariosV1 US,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Division_Origen=OG.CD_Division(+))
	AND (ES.CD_Organo_Origen=OG.CD_Organo(+))
	AND (ES.CD_Organo_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_Organo_Destino=DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+))
	AND (ES.CD_Division_Destino=OG1.CD_Division(+))
	AND (ES.CD_Organo_Destino=OG1.CD_Organo(+))
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES= q
	AND (ES.Fecha BETWEEN fv AND ff)
	-- AND (ES.Num_Registro = r OR r IS NULL)
	AND (ES.Num_Registro = r OR r = 0)
	AND (s IS NOT NULL OR t IS NOT NULL)

ORDER BY  ES.Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_Registro;



/*************************************************************************************************/
PROCEDURE usp_RPT_Registro_fechas(p IN VARCHAR2, q IN VARCHAR2, r IN INTEGER,
kk IN VARCHAR2, cc IN VARCHAR2,x IN VARCHAR2,
--fv in date, ff in date,
cursorOut OUT t_cursor) IS

  ret_cursor t_cursor;

fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

BEGIN
  OPEN ret_cursor FOR


SELECT DISTINCT
	fv AS fechai, ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha ,
	DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,

	(CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' ||
				    MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro AND RINT.CD_INTERESADO = 1
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro AND RINT.CD_INTERESADO = 1
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			END
	ELSE
		CASE WHEN CD_Organo_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro AND RINT.CD_INTERESADO = 1
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				ELSE  OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro AND RINT.CD_INTERESADO = 1
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro AND RINT.CD_INTERESADO = 1
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,

	(CASE WHEN CD_Organo_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
				CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro AND RINT.CD_INTERESADO = 1
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG1.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro AND RINT.CD_INTERESADO = 1
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			END
	ELSE
		CASE WHEN CD_Organo_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo ||
					CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro AND RINT.CD_INTERESADO = 1
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				ELSE  OG1.DS_Organo || ' (' || (SELECT AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG1.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro AND RINT.CD_INTERESADO = 1
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro AND RINT.CD_INTERESADO = 1
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
	DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	ES.Num_Transporte,
	ES.Resumen,
	ES.Estado,
	(select c.DS_Logo
  from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and
    uu.CD_Usuario = x)
   as Organismo
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS OG,  AUX_ORGANOS OG1,AUX_TRANSPORTES TR, UsuariosV1 US,
	DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Division_Origen =OG.CD_Division(+))
	AND (ES.CD_Organo_Origen =OG.CD_Organo(+))
	AND (ES.CD_Organo_Origen =DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen =DEP.CD_Departamento(+))
	AND (ES.CD_Organo_Destino=DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino =DEP1.CD_Departamento(+))
	AND (ES.CD_Division_Destino=OG1.CD_Division(+))
	AND (ES.CD_Organo_Destino =OG1.CD_Organo(+))
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES= q
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND UUU.CD_USUARIO = x
	AND (ES.Fecha BETWEEN fv  AND ff)
	AND (ES.Num_RegistroOF = r OR r = 0)
	AND (fv IS NOT NULL OR ff IS NOT NULL)

ORDER BY  ES.Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_Registro_fechas;



/*************************************************************************************************/
PROCEDURE usp_RPT_Registro_fechas_tc(p IN VARCHAR2, q IN VARCHAR2, r IN INTEGER,
kk IN VARCHAR2, cc IN VARCHAR2,
--fv in date, ff in date,
cursorOut OUT t_cursor) IS

  ret_cursor t_cursor;

fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

BEGIN
  OPEN ret_cursor FOR


SELECT DISTINCT
fv AS fechai, ff AS fechaf,
	ES.Num_RegistroOF,
	RGG.Num_RG,
		(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha ,
	--ES.Fecha AS Fecha ,
	DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
	(CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' ||
				    MIN(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MIN(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			END
	ELSE
		CASE WHEN CD_Organo_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MIN(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				ELSE  OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MIN(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				END
			ELSE (SELECT MIN(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Organo_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
				CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MIN(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
				OG1.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
				CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MIN(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			END
	ELSE
		CASE WHEN CD_Organo_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo ||
					CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MIN(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				ELSE  OG1.DS_Organo || ' (' || (SELECT AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
					OG1.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
					CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MIN(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				END
			ELSE (SELECT MIN(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
	DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	ES.Num_Transporte,
	OFI.Direccion,
	ES.Resumen,
	ES.Estado,
	RINT1.LOCALIDAD,
	CASE WHEN RINT1.PROVINCIA IS NOT NULL
			THEN
	 (SELECT DS_Provincia FROM AUX_PROVINCIAS PROVI WHERE
	  RINT1.PROVINCIA = PROVI.CD_PROVINCIA)
	ELSE
	RINT1.PROVINCIA_EXT
	 END AS Provincia,
	RINT1.PAIS
	/*   TdC  se quita por incidencia 5184
	,CASE WHEN ES.TIPO_TRANSPORTE IS NOT NULL
	 THEN
	  (SELECT DS_TRANSPORTE from  AUX_TRANSPORTES WHERE
	    ES.TIPO_TRANSPORTE = CD_TRANSPORTE)
	ELSE
	' '
	END AS DS_TRANSPORTE
	*/

FROM
	REGISTROES ES, AUX_ORGANOS OG,  AUX_ORGANOS OG1,AUX_OFICINAS OFI,
	REL_INTVSRES RINT1, REGISTROGENERAL RGG, UsuariosV1 US,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Division_Origen =OG.CD_Division(+))
	AND (ES.CD_Organo_Origen =OG.CD_Organo(+))

	AND (ES.CD_Division_Destino=OG1.CD_Division(+))
	AND (ES.CD_Organo_Destino =OG1.CD_Organo(+))

	AND (ES.CD_Organo_Origen =DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen =DEP.CD_Departamento(+))
	AND (ES.CD_Organo_Destino=DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino =DEP1.CD_Departamento(+))

	AND (ES.NUM_REGISTRO = RINT1.NUM_REGISTRO(+))

	AND (ES.TipoES = RINT1.TipoES(+))
	AND RGG.Ejercicio = ES.Ejercicio AND
				RGG.TipoES = ES.TipoES AND RGG.CD_Oficina = ES.CD_Oficina
				AND RGG.Num_Registro = ES.Num_RegistroOF

	AND (TO_NUMBER(TO_CHAR(ES.Fecha,'yyyy')) = rint1.ejercicio(+))
/*	AND (RINT1.CD_INTERESADO = (SELECT MIN(RINT.CD_INTERESADO) FROM REL_INTVSRES RINT
	 WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
*/		AND ES.Usuario = US.DNI
		AND (ES.CD_Oficina = p or p is null)
		AND ES.TipoES= q
	AND (ES.Fecha BETWEEN fv  AND ff)
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (RGG.Num_RG = r OR r = 0)

ORDER BY  RGG.Num_RG;

  cursorOut:=ret_cursor;
END usp_RPT_Registro_fechas_tc;


/*************************************************************************************************/
PROCEDURE usp_RPT_Convocatorias_tc(p IN VARCHAR2, q IN INTEGER, r IN INTEGER,
s IN  VARCHAR2, t IN INTEGER, u IN VARCHAR2, kk IN  VARCHAR2, cc IN  VARCHAR2,
cursorOut OUT t_cursor)
IS
  ret_cursor t_cursor;
  fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
  ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

BEGIN
  OPEN ret_cursor FOR

    SELECT DISTINCT
    	ES.Num_RegistroOF,
    	(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
    				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
    				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
    		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
    		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
    				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
    				AND RG.Num_Registro = ES.Num_RegistroOF))
    		ELSE TO_CHAR(ES.Num_RegistroOF)
    		END) AS NumRegistro,
    	OFI.DS_Oficina,
    	TO_CHAR(ES.Fecha, 'dd/mm/yyyy') AS Fecha,
    	-- ES.Fecha AS Fecha,
    	DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
    	US.DS_Usuario,
    	(CASE WHEN CD_Organo_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
    		THEN CASE WHEN OG.CD_Organosup IS NULL
    			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
    				CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' ||
    				    MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    				ELSE ' '
    				END
    			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
    				OG.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
    				CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    				ELSE ' '
    				END
    			END
    	ELSE
    		CASE WHEN CD_Organo_Origen IS NOT NULL
    			THEN CASE WHEN OG.CD_Organosup IS NULL
    				THEN	OG.DS_Organo ||
    					CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    					ELSE ' '
    					END
    				ELSE  OG.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
    					OG.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
    					CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    					ELSE ' '
    					END
    				END
    			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
    		END
    	END) AS OrgOrigen,
    	(CASE WHEN CD_Organo_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
    		THEN CASE WHEN OG1.CD_Organosup IS NULL
    			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
    				CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    				ELSE ' '
    				END
    			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
    				OG1.CD_Organosup= AUX_OrganosV1.CD_Organo(+)) || ')' ||
    				CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    				ELSE ' '
    				END
    			END
    	ELSE
    		CASE WHEN CD_Organo_Destino IS NOT NULL
    			THEN CASE WHEN OG1.CD_Organosup IS NULL
    				THEN	OG1.DS_Organo ||
    					CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    					ELSE ' '
    					END
    				ELSE  OG1.DS_Organo || ' (' || (SELECT AUX_OrganosV1.DS_Organo FROM AUX_OrganosV1 WHERE
    					OG1.CD_Organosup=AUX_OrganosV1.CD_Organo(+)) || ')' ||
    					CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    					ELSE ' '
    					END
    				END
    			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
    		END
    	END) AS OrgDestino,
    	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
    	DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
    	ES.Num_Regis_Original,
    	ES.Fecha_Regis_Original,
    	ES.Tipo_Transporte,
    	TR.DS_Transporte,
    	ES.Num_Transporte,
    	ES.Resumen,
    	ES.Estado,
    	CASE WHEN ES.NACEXT = 0 THEN 'Nacional'
    	ELSE 'Extranjero'END AS "NAC_EXT",
    	DAT_EXPEDIENTES.DS_Expediente,
    	DAT_EXPEDIENTES.NPL_BOE,
    	TO_CHAR(DAT_EXPEDIENTES.FPL_BOE, 'dd/mm/yyyy') AS FPL_BOE,
    	-- Dat_Expedientes.FPL_BOE,
    	DAT_EXPEDIENTES.NRO_RESOLUCION,
      TO_CHAR(DAT_EXPEDIENTES.F_RESOLUCION, 'dd/mm/yyyy') AS F_RESOLUCION ,
    	-- Dat_Expedientes.F_RESOLUCION,
      REL.CD_Interesado,
    	REL.Nombre,
    	REL.NIF, REL.LOCALIDAD,
      CASE WHEN REL.PROVINCIA IS NOT NULL
    			THEN
    	 (SELECT DS_Provincia FROM AUX_PROVINCIAS PROVI WHERE
    	  REL.PROVINCIA = PROVI.CD_PROVINCIA)
    	ELSE '**'
    	END AS Provincia,
      REL.PAIS,
      REL.CP
    FROM
    	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS OG,  AUX_ORGANOS OG1,AUX_TRANSPORTES TR, UsuariosV1 US,
    	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, DAT_EXPEDIENTES, REL_INTVSRES REL
    WHERE
    	ES.CD_Oficina = OFI.CD_Oficina
    	AND (ES.CD_Division_Origen =OG.CD_Division(+))
    	AND (ES.CD_Organo_Origen =OG.CD_Organo(+))
    	AND (ES.CD_Organo_Origen =DEP.CD_Organo(+))
    	AND (ES.CD_Dep_Origen =DEP.CD_Departamento(+))
    	AND (ES.CD_Organo_Destino=DEP1.CD_Organo(+))
    	AND (ES.CD_Dep_Destino =DEP1.CD_Departamento(+))
    	AND (ES.CD_Division_Destino=OG1.CD_Division(+))
    	AND (ES.CD_Organo_Destino =OG1.CD_Organo(+))
    	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
    	AND ES.Usuario = US.DNI
    	AND OFI.CD_Oficina = p
    	AND (ES.Ejercicio = REL.Ejercicio(+))
    	AND (ES.TipoES = REL.TipoES(+))
      AND (ES.Num_Registro = REL.Num_Registro(+))
    	AND ES.CD_EjercicioExp = DAT_EXPEDIENTES.CD_EjercicioExp
    	AND ES.CD_Expediente = DAT_EXPEDIENTES.CD_Expediente
     	AND ((nvl(ES.CD_Expediente,0) = q ) or	 (NVL(q,0)=0))
    	AND ((NVL(t,0)!= 0 AND DAT_EXPEDIENTES.NPL_BOE = t) OR (NVL(t,0)=0 ))
      AND DAT_EXPEDIENTES.FPL_BOE >= NVL(u,TO_DATE('01/01/2000','dd/mm/yyyy'))
    	AND ((NVL(r,0)!= 0 AND DAT_EXPEDIENTES.NRO_RESOLUCION  = r ) OR 	 (NVL(r,0)=0))
      AND DAT_EXPEDIENTES.F_RESOLUCION >= NVL(s,TO_DATE('01/01/2000','dd/mm/yyyy'))
    	--	AND (ES.Fecha BETWEEN fv  AND ff)
    	--  AND (fv IS NOT NULL OR ff IS NOT NULL)
    	AND (ES.Fecha >= fv  or fv is null)
    	AND (ES.Fecha <= ff or ff is null)
    ORDER BY Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_Convocatorias_tc;



/*************************************************************************************************/
PROCEDURE usp_RPT_Convocatorias_MIN(p IN VARCHAR2, q IN INTEGER, r IN INTEGER,
s IN  VARCHAR2, t IN INTEGER, u IN VARCHAR2, kk IN  VARCHAR2, cc IN  VARCHAR2,
cursorOut OUT t_cursor)
IS
  ret_cursor t_cursor;
  fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
  ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

BEGIN
  OPEN ret_cursor FOR

    SELECT DISTINCT
    	ES.Num_RegistroOF,
    	(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
    				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
    				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
    		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
    		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
    				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
    				AND RG.Num_Registro = ES.Num_RegistroOF))
    		ELSE TO_CHAR(ES.Num_RegistroOF)
    		END) AS NumRegistro,
    	OFI.DS_Oficina,
    	TO_CHAR(ES.Fecha, 'dd/mm/yyyy') AS Fecha,
    	DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
    	US.DS_Usuario,
    	(CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
    		THEN CASE WHEN OG.CD_Organosup IS NULL
    			THEN DEP.DS_Departamento || '.' || OG.DS_Organo ||
    				CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre)
              FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    				ELSE ' '
    				END
    			ELSE DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
          (SELECT DS_Organo FROM AUX_Organos_PROD WHERE
    				OG.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)) || ')' ||
    				CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre)
             FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    				ELSE ' '
    				END
    			END
    	ELSE
    		CASE WHEN ES.CD_Org_Origen IS NOT NULL
    			THEN CASE WHEN OG.CD_Organosup IS NULL
    				THEN OG.DS_Organo ||
    					CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre)
               FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    					ELSE ' '
    					END
    				ELSE OG.DS_Organo || ' (' || (SELECT DS_Organo FROM AUX_Organos_PROD
             WHERE OG.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)) || ')' ||
    					CASE WHEN ES.TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre)
               FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    					ELSE ' '
    					END
    				END
    			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT
           WHERE RINT.Ejercicio = ES.Ejercicio AND
    				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
    		END
    	END) AS OrgOrigen,
    	(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
    		THEN CASE WHEN OG1.CD_Organosup IS NULL
    			THEN DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
    				CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre)
             FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    				ELSE ' '
    				END
    			ELSE DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
           (SELECT DS_Organo FROM AUX_Organos_PROD WHERE
    				OG1.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)) || ')' ||
    				CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre)
             FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    				ELSE ' '
    				END
    			END
    	ELSE
    		CASE WHEN CD_Org_Destino IS NOT NULL
    			THEN CASE WHEN OG1.CD_Organosup IS NULL
    				THEN OG1.DS_Organo ||
    					CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre)
               FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    					ELSE ' '
    					END
    				ELSE OG1.DS_Organo || ' (' || (SELECT DS_Organo FROM AUX_Organos_PROD WHERE
    					OG1.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)) || ')' ||
    					CASE WHEN ES.TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre)
               FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
    						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
    					ELSE ' '
    					END
    				END
    			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT
           WHERE RINT.Ejercicio = ES.Ejercicio AND
    				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
    				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
    		END
    	END) AS OrgDestino,
    	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
    	DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
    	ES.Num_Regis_Original,
    	ES.Fecha_Regis_Original,
    	ES.Tipo_Transporte,
    	TR.DS_Transporte,
    	ES.Num_Transporte,
    	ES.Resumen,
    	ES.Estado,
    	CASE WHEN ES.NACEXT = 0 THEN 'Nacional'
    	ELSE 'Extranjero'END AS "NAC_EXT",
    	DAT_EXPEDIENTES.DS_Expediente,
    	DAT_EXPEDIENTES.NPL_BOE,
    	TO_CHAR(DAT_EXPEDIENTES.FPL_BOE, 'dd/mm/yyyy') AS FPL_BOE,
    	DAT_EXPEDIENTES.NRO_RESOLUCION,
      TO_CHAR(DAT_EXPEDIENTES.F_RESOLUCION, 'dd/mm/yyyy') AS F_RESOLUCION ,
      REL.CD_Interesado,
    	REL.Nombre,
    	REL.NIF, REL.LOCALIDAD,
      CASE WHEN REL.PROVINCIA IS NOT NULL
    		THEN
      	 (SELECT DS_Provincia FROM AUX_PROVINCIAS PROVI WHERE
      	  REL.PROVINCIA = PROVI.CD_PROVINCIA)
    	  ELSE '**'
    	END AS Provincia,
      REL.PAIS,
      REL.CP
    FROM
    	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS_PROD OG, AUX_ORGANOS_PROD OG1,
      AUX_TRANSPORTES TR, UsuariosV1 US, AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1,
      DAT_EXPEDIENTES, REL_INTVSRES REL
    WHERE
    	ES.CD_Oficina = OFI.CD_Oficina
    	AND (ES.CD_Div_Origen = OG.CD_Division(+))
    	AND (ES.CD_Org_Origen = OG.CD_Organo(+))
    	AND (ES.CD_Org_Origen = DEP.CD_Organo(+))
    	AND (ES.CD_Dep_Origen = DEP.CD_Departamento(+))
    	AND (ES.CD_Org_Destino = DEP1.CD_Organo(+))
    	AND (ES.CD_Dep_Destino = DEP1.CD_Departamento(+))
    	AND (ES.CD_Div_Destino = OG1.CD_Division(+))
    	AND (ES.CD_Org_Destino = OG1.CD_Organo(+))
    	AND (ES.Tipo_Transporte = TR.CD_Transporte(+))
    	AND ES.Usuario = US.DNI
    	AND OFI.CD_Oficina = p
    	AND (ES.Ejercicio = REL.Ejercicio(+))
    	AND (ES.TipoES = REL.TipoES(+))
      AND (ES.Num_Registro = REL.Num_Registro(+))
    	AND ES.CD_EjercicioExp = DAT_EXPEDIENTES.CD_EjercicioExp
    	AND ES.CD_Expediente = DAT_EXPEDIENTES.CD_Expediente
     	AND ((nvl(ES.CD_Expediente,0) = q) or	(NVL(q,0)=0))
    	AND ((NVL(t,0)!= 0 AND DAT_EXPEDIENTES.NPL_BOE = t) OR (NVL(t,0)=0 ))
      AND DAT_EXPEDIENTES.FPL_BOE >= NVL(u,TO_DATE('01/01/2000','dd/mm/yyyy'))
    	AND ((NVL(r,0)!= 0 AND DAT_EXPEDIENTES.NRO_RESOLUCION = r) OR (NVL(r,0)=0))
      AND DAT_EXPEDIENTES.F_RESOLUCION >= NVL(s,TO_DATE('01/01/2000','dd/mm/yyyy'))
    	AND (ES.Fecha >= fv  or fv is null)
    	AND (ES.Fecha <= ff or ff is null)
    ORDER BY Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_Convocatorias_MIN;



/*************************************************************************************************/
PROCEDURE usp_RPT_Registro_fechas_MIN(p IN VARCHAR2, q IN VARCHAR2, r IN NUMBER,
kk IN VARCHAR2, cc IN VARCHAR2,x IN VARCHAR2,
--fv in date, ff in date,
cursorOut OUT t_cursor) IS

  ret_cursor t_cursor;

fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

 BEGIN
  OPEN ret_cursor FOR


SELECT DISTINCT
	fv AS fechai, ff AS fechaf,
	ES.Num_RegistroOF,
	(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha ,
	DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
	(CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo ||
				CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' ||
				    MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
				OG.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG.CD_Organosup= VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
				CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo ||
					CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				ELSE  OG.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
					OG.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG.CD_Organosup=VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
					CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
				CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
				OG1.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG1.CD_Organosup= VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
				CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE ' '
				END
			END
	ELSE
		CASE WHEN CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo ||
					CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				ELSE  OG1.DS_Organo || ' (' || (SELECT VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
					OG1.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG1.CD_Organosup=VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
					CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
					ELSE ' '
					END
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
	DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,
	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	ES.Num_Transporte,
	ES.Resumen,
	ES.Estado,
	(select c.DS_Logo
  from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and
    uu.CD_Usuario = x)
   as Organismo
FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS_PROD OG,  AUX_ORGANOS_PROD OG1,AUX_TRANSPORTES TR, UsuariosV1 US,
	DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_DIV_ORIGEN =OG.CD_Division(+))
	AND (ES.CD_ORG_ORIGEN =OG.CD_Organo(+))
	AND (ES.CD_ORG_ORIGEN =DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen =DEP.CD_Departamento(+))
	AND (ES.CD_ORG_DESTINO=DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino =DEP1.CD_Departamento(+))
	AND (ES.CD_DIV_DESTINO=OG1.CD_Division(+))
	AND (ES.CD_ORG_DESTINO =OG1.CD_Organo(+))
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES= q
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND UUU.CD_USUARIO = x
	AND (ES.Fecha BETWEEN fv  AND ff)
	AND (ES.Num_RegistroOF = r OR r = 0)
	AND (fv IS NOT NULL OR ff IS NOT NULL)

ORDER BY  ES.Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_Registro_fechas_MIN;



/*************************************************************************************************/
PROCEDURE usp_RPT_Registro_fechas_MAP(p IN VARCHAR2, q IN VARCHAR2, r IN NUMBER,
kk IN VARCHAR2, cc IN VARCHAR2,x IN VARCHAR2, v IN VARCHAR2, w IN VARCHAR2, P_TipoAsunto IN INTEGER,
cursorOut OUT t_cursor) IS


  ret_cursor t_cursor;

fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

hay_trans INTEGER;
ds_trans VARCHAR2(60);
ds_trans1 VARCHAR2(60);
ds_trans2 VARCHAR2(60);

BEGIN

      BEGIN
          SELECT DS_TRANSPORTE INTO ds_trans1
          FROM AUX_TRANSPORTES WHERE CD_TRANSPORTE = v;
      EXCEPTION
          WHEN NO_DATA_FOUND
              THEN ds_trans1 := NULL;
      END;

      BEGIN
          SELECT DS_TRANSPORTE INTO ds_trans2
          FROM AUX_TRANSPORTES WHERE CD_TRANSPORTE = w;
      EXCEPTION
          WHEN NO_DATA_FOUND
              THEN ds_trans2 := NULL;
      END;

      IF v IS NULL AND w IS NULL THEN
            hay_trans := 0;
            ds_trans := NULL;
      ELSE
            hay_trans := 1;
            IF v IS NULL THEN
                ds_trans := ds_trans2;
            ELSE
                IF w IS NULL THEN
                    ds_trans := ds_trans1;
                ELSE
                    ds_trans := ds_trans1 || ' / ' || ds_trans2;
                END IF;
            END IF;
      END IF;


  OPEN ret_cursor FOR

SELECT DISTINCT
    fv AS fechai, ff AS fechaf,
    ES.Num_RegistroOF,
    (CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
                RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
        THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
             TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
                RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                AND RG.Num_Registro = ES.Num_RegistroOF))
        ELSE TO_CHAR(ES.Num_RegistroOF)
        END) AS NumRegistro,
    OFI.DS_Oficina,
    ES.FechaP AS Fecha ,
    DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
    US.DS_Usuario,
    (CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
        THEN CASE WHEN OG.CD_Organosup IS NULL
            THEN    DEP.DS_Departamento || '.' || OG.DS_Organo ||
                CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' ||
                    MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                    RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                ELSE ' '
                END
            ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
                OG.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG.CD_Organosup= VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
                CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                    RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                ELSE ' '
                END
            END
    ELSE
        CASE WHEN ES.CD_Org_Origen IS NOT NULL
            THEN CASE WHEN OG.CD_Organosup IS NULL
                THEN    OG.DS_Organo ||
                    CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                    ELSE ' '
                    END
                ELSE  OG.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
                    OG.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG.CD_Organosup=VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
                    CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                    ELSE ' '
                    END
                END
            ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
        END
    END) AS OrgOrigen,
    (CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
        THEN CASE WHEN OG1.CD_Organosup IS NULL
            THEN    DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
                CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                    RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                ELSE ' '
                END
            ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
                OG1.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG1.CD_Organosup= VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
                CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                    RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                ELSE ' '
                END
            END
    ELSE
        CASE WHEN CD_Org_Destino IS NOT NULL
            THEN CASE WHEN OG1.CD_Organosup IS NULL
                THEN    OG1.DS_Organo ||
                    CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                    ELSE ' '
                    END
                ELSE  OG1.DS_Organo || ' (' || (SELECT VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
                    OG1.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG1.CD_Organosup=VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
                    CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                    ELSE ' '
                    END
                END
            ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
        END
    END) AS OrgDestino,
    ' /' || ES.CD_Regis_Original AS CD_Regis_Original,
    DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
    ES.Num_Regis_Original,
    ES.Fecha_Regis_Original,
    ES.Tipo_Transporte,
    TR.DS_Transporte,
    ES.Num_Transporte,
    NVL(CM.CONTENIDO,'') || ' ' || ES.Resumen AS Resumen,
    ES.Estado,
    (select c.DS_Logo
  from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and
    uu.CD_Usuario = x)
   as Organismo,
   ES.NUM_DOCUMENTOS,
   hay_trans as FILTRO_TRANSPORTE,
   ds_trans as DS_FILTRO_TRANSPORTE,
    CASE WHEN P_TipoAsunto IS NOT NULL
       THEN CM.DS_COMENTARIO
       ELSE ''
    END AS TipoAsunto

FROM
    AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS_PROD OG,  AUX_ORGANOS_PROD OG1,AUX_TRANSPORTES TR, UsuariosV1 US,
    DAT_CONFIG CC, USUARIOS uuu,
    AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, AUX_COMENTARIOS CM
WHERE
    ES.CD_Oficina = OFI.CD_Oficina
    AND (ES.CD_DIV_ORIGEN =OG.CD_Division(+))
    AND (ES.CD_ORG_ORIGEN =OG.CD_Organo(+))
    AND (ES.CD_ORG_ORIGEN =DEP.CD_Organo(+))
    AND (ES.CD_Dep_Origen =DEP.CD_Departamento(+))
    AND (ES.CD_ORG_DESTINO=DEP1.CD_Organo(+))
    AND (ES.CD_Dep_Destino =DEP1.CD_Departamento(+))
    AND (ES.CD_DIV_DESTINO=OG1.CD_Division(+))
    AND (ES.CD_ORG_DESTINO =OG1.CD_Organo(+))
    AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
    AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
    AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
    AND ES.Usuario = US.DNI
    AND OFI.CD_Oficina = p
    AND ES.TipoES= q
    AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
    AND UUU.CD_USUARIO = x
    --AND (ES.Fecha BETWEEN fv  AND ff)
    --AND (TO_DATE(TO_CHAR(ES.Fecha,'DD/MM/YYYY')) >= fv)-- OR fv is null)
    --AND (TO_DATE(TO_CHAR(ES.Fecha,'DD/MM/YYYY')) <= ff)-- OR ff is null)
    AND (ES.Fecha >= fv  AND ES.Fecha <= ff)
    AND (ES.Num_RegistroOF = r OR r = 0)
    --AND (fv IS NOT NULL OR ff IS NOT NULL)
  AND ((ES.TIPO_TRANSPORTE IN (v,w)) OR hay_trans = 0)
  AND (ES.CD_TIPOASUNTO_COM = P_TipoAsunto OR P_TipoAsunto is null OR P_TipoAsunto = 0)


ORDER BY  ES.Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_Registro_fechas_MAP;

/*************************************************************************************************/

PROCEDURE usp_RPT_Registro_FPre_2200(p IN VARCHAR2, q IN VARCHAR2, r IN NUMBER,
kk IN VARCHAR2, cc IN VARCHAR2,x IN VARCHAR2, v IN VARCHAR2, w IN VARCHAR2, P_TipoAsunto IN INTEGER,
cursorOut OUT t_cursor) IS

  ret_cursor t_cursor;

fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

hay_trans INTEGER;
ds_trans VARCHAR2(60);
ds_trans1 VARCHAR2(60);
ds_trans2 VARCHAR2(60);

BEGIN

      BEGIN
          SELECT DS_TRANSPORTE INTO ds_trans1
          FROM AUX_TRANSPORTES WHERE CD_TRANSPORTE = v;
      EXCEPTION
          WHEN NO_DATA_FOUND
              THEN ds_trans1 := NULL;
      END;

      BEGIN
          SELECT DS_TRANSPORTE INTO ds_trans2
          FROM AUX_TRANSPORTES WHERE CD_TRANSPORTE = w;
      EXCEPTION
          WHEN NO_DATA_FOUND
              THEN ds_trans2 := NULL;
      END;

      IF v IS NULL AND w IS NULL THEN
            hay_trans := 0;
            ds_trans := NULL;
      ELSE
            hay_trans := 1;
            IF v IS NULL THEN
                ds_trans := ds_trans2;
            ELSE
                IF w IS NULL THEN
                    ds_trans := ds_trans1;
                ELSE
                    ds_trans := ds_trans1 || ' / ' || ds_trans2;
                END IF;
            END IF;
      END IF;


  OPEN ret_cursor FOR

SELECT DISTINCT
    fv AS fechai, ff AS fechaf,
    ES.Num_RegistroOF,
    (CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
                RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
        THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
             TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
                RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                AND RG.Num_Registro = ES.Num_RegistroOF))
        ELSE TO_CHAR(ES.Num_RegistroOF)
        END) AS NumRegistro,
    OFI.DS_Oficina,
    ES.Fecha_Presen AS Fecha ,
    DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
    US.DS_Usuario,
    (CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
        THEN CASE WHEN OG.CD_Organosup IS NULL
            THEN    DEP.DS_Departamento || '.' || OG.DS_Organo ||
                CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' ||
                    MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                    RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                ELSE ' '
                END
            ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
                OG.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG.CD_Organosup= VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
                CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                    RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                ELSE ' '
                END
            END
    ELSE
        CASE WHEN ES.CD_Org_Origen IS NOT NULL
            THEN CASE WHEN OG.CD_Organosup IS NULL
                THEN    OG.DS_Organo ||
                    CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                    ELSE ' '
                    END
                ELSE  OG.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
                    OG.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG.CD_Organosup=VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
                    CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                    ELSE ' '
                    END
                END
            ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
        END
    END) AS OrgOrigen,
    (CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
        THEN CASE WHEN OG1.CD_Organosup IS NULL
            THEN    DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
                CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                    RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                ELSE ' '
                END
            ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
                OG1.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG1.CD_Organosup= VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
                CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                    RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                ELSE ' '
                END
            END
    ELSE
        CASE WHEN CD_Org_Destino IS NOT NULL
            THEN CASE WHEN OG1.CD_Organosup IS NULL
                THEN    OG1.DS_Organo ||
                    CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                    ELSE ' '
                    END
                ELSE  OG1.DS_Organo || ' (' || (SELECT VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
                    OG1.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG1.CD_Organosup=VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
                    CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                    ELSE ' '
                    END
                END
            ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
        END
    END) AS OrgDestino,
    ' /' || ES.CD_Regis_Original AS CD_Regis_Original,
    DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
    ES.Num_Regis_Original,
    ES.Fecha_Regis_Original,
    ES.Tipo_Transporte,
    TR.DS_Transporte,
    ES.Num_Transporte,
    NVL(CM.CONTENIDO,'') || ' ' || ES.Resumen AS Resumen,
    ES.Estado,
    (select c.DS_Logo
  from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and
    uu.CD_Usuario = x)
   as Organismo,
   ES.NUM_DOCUMENTOS,
   hay_trans as FILTRO_TRANSPORTE,
   ds_trans as DS_FILTRO_TRANSPORTE,
    CASE WHEN P_TipoAsunto IS NOT NULL
       THEN CM.DS_COMENTARIO
       ELSE ''
    END AS TipoAsunto

FROM
    AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS_PROD OG,  AUX_ORGANOS_PROD OG1,AUX_TRANSPORTES TR, UsuariosV1 US,
    DAT_CONFIG CC, USUARIOS uuu,
    AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, AUX_COMENTARIOS CM
WHERE
    ES.CD_Oficina = OFI.CD_Oficina
    AND (ES.CD_DIV_ORIGEN =OG.CD_Division(+))
    AND (ES.CD_ORG_ORIGEN =OG.CD_Organo(+))
    AND (ES.CD_ORG_ORIGEN =DEP.CD_Organo(+))
    AND (ES.CD_Dep_Origen =DEP.CD_Departamento(+))
    AND (ES.CD_ORG_DESTINO=DEP1.CD_Organo(+))
    AND (ES.CD_Dep_Destino =DEP1.CD_Departamento(+))
    AND (ES.CD_DIV_DESTINO=OG1.CD_Division(+))
    AND (ES.CD_ORG_DESTINO =OG1.CD_Organo(+))
    AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
    AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
    AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
    AND ES.Usuario = US.DNI
    AND OFI.CD_Oficina = p
    AND ES.TipoES= q
    AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
    AND UUU.CD_USUARIO = x
    --AND (ES.Fecha BETWEEN fv  AND ff)
    --AND (TO_DATE(TO_CHAR(ES.Fecha_presen,'DD/MM/YYYY')) >= fv)-- OR fv is null)
    --AND (TO_DATE(TO_CHAR(ES.Fecha_presen,'DD/MM/YYYY')) <= ff)-- OR ff is null)
    AND (ES.Fecha_Presen_SH >= fv  AND ES.Fecha_Presen_SH <= ff)
    AND (ES.Num_RegistroOF = r OR r = 0)
    --AND (fv IS NOT NULL OR ff IS NOT NULL)
  AND ((ES.TIPO_TRANSPORTE IN (v,w)) OR hay_trans = 0)
  AND (ES.CD_TIPOASUNTO_COM = P_TipoAsunto OR P_TipoAsunto is null OR P_TipoAsunto = 0)


ORDER BY  ES.Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_Registro_FPre_2200;

/*************************************************************************************************/
PROCEDURE usp_RPT_IndicesSDI_2_MIN(p IN VARCHAR2,
--fv in date,ff in date,
kk IN VARCHAR2, cc IN VARCHAR2,
s IN NUMBER, t IN NUMBER, u IN VARCHAR2,  cursorOut OUT t_cursor) IS

  ret_cursor t_cursor;
fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

 BEGIN

  OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
		(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha ,
	DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
    (CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
				OG.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG.CD_Organosup= VS_ORGANOS_PROD3.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo
				ELSE  OG.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
					OG.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG.CD_Organosup=VS_ORGANOS_PROD3.CD_Organo(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
				OG1.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG1.CD_Organosup= VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG1.DS_Organo || ' (' || (SELECT VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
					OG1.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG1.CD_Organosup=VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	CASE WHEN (ES.CD_Org_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
	(SELECT MAX(LOCALIDAD)  FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS LOCALIDAD,
	CASE WHEN  (ES.CD_Org_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
	(SELECT MAX(PAIS)  FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS PAIS,
	CASE WHEN  (ES.CD_Org_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
		(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	END AS PROVINCIA,
	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
    DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,

	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	OFI.Direccion,
	ES.Num_Transporte,
	ES.Resumen,
	ES.CD_Organo_Destino,
	(select c.DS_Logo
  from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and
    uu.CD_Usuario = u)
   as Organismo

FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS_PROD OG,
	 AUX_ORGANOS_PROD OG1,AUX_TRANSPORTES TR, UsuariosV1 US,
	DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_DIV_ORIGEN = OG.CD_Division)
	AND (ES.CD_ORG_ORIGEN = OG.CD_Organo)
	AND (ES.CD_ORG_ORIGEN=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_ORG_DESTINO =DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+))
	AND (ES.CD_DIV_DESTINO=OG1.CD_Division(+))
	AND (ES.CD_ORG_DESTINO =OG1.CD_Organo(+))
	AND (ES.Tipo_Transporte =TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'S'
	AND (ES.Fecha  BETWEEN fv AND ff)
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND UUU.CD_USUARIO = u
	AND ES.Estado IS NULL
	AND ES.CD_Organo_Destino IS  NULL

ORDER BY  OrgOrigen,Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_IndicesSDI_2_MIN;

/*************************************************************************************************/
PROCEDURE usp_RPT_IndicesSDI_2_2200(p IN VARCHAR2,
--fv in date,ff in date,
kk IN VARCHAR2, cc IN VARCHAR2,
s IN NUMBER, t IN NUMBER, u IN VARCHAR2, v IN VARCHAR2, w IN VARCHAR2, cursorOut OUT t_cursor) IS

  ret_cursor t_cursor;
fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

v_ta varchar(5);

 BEGIN

 if v is not null then
          v_ta := substr(v,instr(v, '|', 1, 1) + 1,
                                 length(v) - instr(v, '|', 1, 1));
  end if;

  OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
		(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha ,
	DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
    (CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
				OG.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG.CD_Organosup= VS_ORGANOS_PROD3.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo
				ELSE  OG.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
					OG.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG.CD_Organosup=VS_ORGANOS_PROD3.CD_Organo(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
				OG1.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG1.CD_Organosup= VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG1.DS_Organo || ' (' || (SELECT VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
					OG1.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG1.CD_Organosup=VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	CASE WHEN (ES.CD_Org_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
	(SELECT MAX(LOCALIDAD)  FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS LOCALIDAD,
	CASE WHEN  (ES.CD_Org_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
	(SELECT MAX(PAIS)  FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS PAIS,
	CASE WHEN  (ES.CD_Org_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
		(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	END AS PROVINCIA,
	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
    DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,

	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	OFI.Direccion,
	ES.Num_Transporte,
	ES.Resumen,
	ES.CD_Organo_Destino,
	(select c.DS_Logo
  from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and
    uu.CD_Usuario = u)
   as Organismo,
   CASE WHEN v_ta IS NOT NULL
	   THEN CM.DS_COMENTARIO
	   ELSE ''
	END AS TipoAsunto

FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS_PROD OG,
	 AUX_ORGANOS_PROD OG1,AUX_TRANSPORTES TR, UsuariosV1 US,
	DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, AUX_COMENTARIOS CM
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_DIV_ORIGEN = OG.CD_Division)
	AND (ES.CD_ORG_ORIGEN = OG.CD_Organo)
	AND (ES.CD_ORG_ORIGEN=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_ORG_DESTINO =DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+))
	AND (ES.CD_DIV_DESTINO=OG1.CD_Division(+))
	AND (ES.CD_ORG_DESTINO =OG1.CD_Organo(+))
	AND (ES.Tipo_Transporte =TR.CD_Transporte(+))
	AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
	AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
	AND ES.Usuario = US.DNI
	AND ES.Usuario = US.DNI
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'S'
	AND (ES.Fecha  BETWEEN fv AND ff)
	--AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND UUU.CD_USUARIO = u
	AND ES.Estado IS NULL
	AND ES.CD_Organo_Destino IS  NULL
	AND ((v_ta IS NOT NULL AND TO_CHAR(ES.CD_TIPOASUNTO_COM) = v_ta) OR
               (v_ta is null))
  AND ((DEP.CD_Departamento = w or w is null) OR (DEP1.CD_Departamento = w or w is null))


ORDER BY  OrgOrigen,Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_IndicesSDI_2_2200;


/*************************************************************************************************/

PROCEDURE usp_RPT_IndicesSDI_FPre_2200(p IN VARCHAR2,
--fv in date,ff in date,
kk IN VARCHAR2, cc IN VARCHAR2,
s IN NUMBER, t IN NUMBER, u IN VARCHAR2, v IN VARCHAR2, cursorOut OUT t_cursor) IS

  ret_cursor t_cursor;
fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

v_ta varchar(5);

 BEGIN

 if v is not null then
          v_ta := substr(v,instr(v, '|', 1, 1) + 1,
                                 length(v) - instr(v, '|', 1, 1));
  end if;

  OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
		(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.Fecha_Presen AS Fecha ,
	DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
    (CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
				OG.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG.CD_Organosup= VS_ORGANOS_PROD3.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo
				ELSE  OG.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
					OG.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG.CD_Organosup=VS_ORGANOS_PROD3.CD_Organo(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
				OG1.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG1.CD_Organosup= VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG1.DS_Organo || ' (' || (SELECT VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
					OG1.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG1.CD_Organosup=VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	CASE WHEN (ES.CD_Org_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
	(SELECT MAX(LOCALIDAD)  FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS LOCALIDAD,
	CASE WHEN  (ES.CD_Org_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
	(SELECT MAX(PAIS)  FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS PAIS,
	CASE WHEN  (ES.CD_Org_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
		(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	END AS PROVINCIA,
	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
    DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,

	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	OFI.Direccion,
	ES.Num_Transporte,
	ES.Resumen,
	ES.CD_Organo_Destino,
	(select c.DS_Logo
  from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and
    uu.CD_Usuario = u)
   as Organismo,
   CASE WHEN v_ta IS NOT NULL
	   THEN CM.DS_COMENTARIO
	   ELSE ''
	END AS TipoAsunto

FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS_PROD OG,
	 AUX_ORGANOS_PROD OG1,AUX_TRANSPORTES TR, UsuariosV1 US,
	DAT_CONFIG CC, USUARIOS uuu,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1, AUX_COMENTARIOS CM
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_DIV_ORIGEN = OG.CD_Division)
	AND (ES.CD_ORG_ORIGEN = OG.CD_Organo)
	AND (ES.CD_ORG_ORIGEN=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_ORG_DESTINO =DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+))
	AND (ES.CD_DIV_DESTINO=OG1.CD_Division(+))
	AND (ES.CD_ORG_DESTINO =OG1.CD_Organo(+))
	AND (ES.Tipo_Transporte =TR.CD_Transporte(+))
	AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
	AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
	AND ES.Usuario = US.DNI
	AND ES.Usuario = US.DNI
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'S'
	--AND (TRUNC(ES.Fecha_presen) >= fv and trunc(ES.Fecha_presen) <= ff)
  AND (ES.Fecha_presen_sh >= fv and ES.Fecha_presen_sh <= ff)
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND UUU.CD_USUARIO = u
	AND ES.Estado IS NULL
	AND ES.CD_Organo_Destino IS  NULL
	AND ((v_ta IS NOT NULL AND TO_CHAR(ES.CD_TIPOASUNTO_COM) = v_ta) OR
               (v_ta is null))


ORDER BY  OrgOrigen,Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_IndicesSDI_FPre_2200;

/*************************************************************************************************/
PROCEDURE usp_RPT_FichaRegistro_MIN(p IN INTEGER,q IN VARCHAR2, r IN INTEGER,
                               s IN VARCHAR2, cursorOut OUT t_cursor) IS
  ret_cursor t_cursor;

BEGIN
  OPEN ret_cursor FOR


SELECT DISTINCT ES.*,
	s AS Firmante,
	DECODE(ES.TipoES,'E',
		'Remitentes:',
		 'Destinatarios:') AS Desc_Interesados,
	OFI.DS_Oficina,
	OFI.Poblacion,
	OFI.Direccion,
	OFI.Telefono,
	OFI.Fax,
	OFI.CP,
	PR.DS_Provincia,
	TRE.DS_TipoRegistro,
	TR.DS_Transporte,
	TRE1.DS_TipoRegistro AS DS_TipoRegOriginal,
		(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	(CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
			(SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
				OG.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG.CD_Organosup= VS_ORGANOS_PROD3.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo
				ELSE  OG.DS_Organo || ' (' || (SELECT  UPPER(VS_ORGANOS_PROD3.DS_Organo) FROM VS_ORGANOS_PROD3 WHERE
					OG.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG.CD_Organosup=VS_ORGANOS_PROD3.CD_Organo(+)) || ')'
				END

		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
			(SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
				OG1.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG1.CD_Organosup= VS_ORGANOS_PROD3.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo
				ELSE  OG1.DS_Organo || ' (' || (SELECT VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
					OG1.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG1.CD_Organosup=VS_ORGANOS_PROD3.CD_Organo(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	TO_CHAR(SYSDATE, 'DD') || ' de ' ||
     DECODE(TO_CHAR(SYSDATE, 'MM'),01,'Enero'
	 ,02,'Febrero'
	 ,03,'Marzo'
	 ,04 , 'Abril'
	 ,05 , 'Mayo'
	 ,06 , 'Junio'
	 ,07 , 'Julio'
	 ,08 , 'Agosto'
	 ,09 ,'Septiembre'
	 ,10 ,'Octubre'
	 ,11 , 'Noviembre'
	 ,12 , 'Diciembre', ' ')
|| ' de ' || TO_CHAR(SYSDATE, 'YYYY') AS FechaHoy,
 'MINISTERIO DEL INTERIOR' as Organismo
FROM
      	REGISTROES ES, --REL_INTvsREs IR,
	AUX_OFICINAS OFI, AUX_PROVINCIAS PR,
	AUX_TIPOREGISTRO TRE, AUX_TIPOREGISTRO TRE1,AUX_TRANSPORTES TR,  AUX_ORGANOS_PROD OG,
	AUX_ORGANOS_PROD OG1,
	AUX_DEPARTAMENTOS DEP,
	AUX_DEPARTAMENTOS DEP1

WHERE

	ES.Ejercicio = p
	AND ES.TipoES = q
	AND ES.Num_Registro = r
	AND ES.CD_Oficina = OFI.CD_Oficina
	AND (OFI.Provincia=PR.CD_Provincia(+))
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND ES.TipoES = TRE.CD_TipoRegistro
	AND (ES.CD_DIV_ORIGEN = OG.CD_Division(+))
	AND (ES.CD_ORG_ORIGEN=OG.CD_Organo(+))
	AND (ES.CD_DIV_DESTINO=OG1.CD_Division(+))
	AND (ES.CD_ORG_DESTINO=OG1.CD_Organo(+))
	AND (ES.Tipo_Regis_Original=TRE1.CD_TipoRegistro(+))
	AND (ES.CD_ORG_ORIGEN=DEP.CD_DIVISION(+))
	AND (ES.CD_ORG_ORIGEN=DEP.CD_Organo(+))
	AND (ES.CD_DIV_DESTINO=DEP1.CD_DIVISION(+))
	AND (ES.CD_ORG_DESTINO=DEP1.CD_Organo(+));


  cursorOut:=ret_cursor;
END usp_RPT_FichaRegistro_MIN;


/*************************************************************************************************/
PROCEDURE usp_RPT_IndicesSDI_MIN(p IN VARCHAR2,
--fv in date,ff in date,
kk IN VARCHAR2, cc IN VARCHAR2,
s IN INTEGER, t IN INTEGER, cursorOut OUT t_cursor) IS

  ret_cursor t_cursor;
fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

 BEGIN

  OPEN ret_cursor FOR

SELECT DISTINCT
	fv AS fechai,ff AS fechaf,
	ES.Num_RegistroOF,
		(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	ES.NUM_REGIS_ORIGINAL AS NumRegistrorem,
	ES.FECHA_REGIS_ORIGINAL AS Fecharem,
	OFI.DS_Oficina,
	ES.FechaP AS Fecha ,
	DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
	US.DS_Usuario,
    (CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
				OG.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG.CD_Organosup= VS_ORGANOS_PROD3.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN	OG.DS_Organo
				ELSE  OG.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
					OG.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG.CD_Organosup=VS_ORGANOS_PROD3.CD_Organo(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
				OG1.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG1.CD_Organosup= VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
				NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
					RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
					GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
			END
	ELSE
		CASE WHEN CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				ELSE  OG1.DS_Organo || ' (' || (SELECT VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
					OG1.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG1.CD_Organosup=VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
					NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
						RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
						GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	CASE WHEN (ES.CD_Org_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
	(SELECT MAX(LOCALIDAD)  FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS LOCALIDAD,
	CASE WHEN  (ES.CD_Org_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
	(SELECT MAX(PAIS)  FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END AS PAIS,
	CASE WHEN  (ES.CD_Org_Origen  IS NULL  AND CD_Dep_Origen IS NULL)  OR  (CD_Organo_Origen IS NOT NULL)
	THEN
		(SELECT DS_PROVINCIA FROM AUX_PROVINCIAS WHERE CD_PROVINCIA =
	  (SELECT MAX(PROVINCIA) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro))
	END AS PROVINCIA,
	' /' || ES.CD_Regis_Original AS CD_Regis_Original,
    DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
	ES.Num_Regis_Original,

	ES.Fecha_Regis_Original,
	ES.Tipo_Transporte,
	TR.DS_Transporte,
	OFI.Direccion,
	ES.Num_Transporte,
	ES.Resumen,
	ES.CD_Organo_Destino

FROM
	AUX_OFICINAS OFI, REGISTROES ES, AUX_ORGANOS_PROD OG,
	 AUX_ORGANOS_PROD OG1,AUX_TRANSPORTES TR, UsuariosV1 US,
	AUX_DEPARTAMENTOS DEP, AUX_DEPARTAMENTOS DEP1
WHERE
	ES.CD_Oficina = OFI.CD_Oficina
	AND (ES.CD_Division_Origen = OG.CD_Division)
	AND (ES.CD_Organo_Origen = OG.CD_Organo)
	AND (ES.CD_Organo_Origen=DEP.CD_Organo(+))
	AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_Organo_Destino =DEP1.CD_Organo(+))
	AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+))
	AND (ES.CD_Division_Destino=OG1.CD_Division(+))
	AND (ES.CD_Organo_Destino =OG1.CD_Organo(+))
	AND (ES.Tipo_Transporte =TR.CD_Transporte(+))
	AND ES.Usuario = US.DNI
	AND OFI.CD_Oficina = p
	AND ES.TipoES = 'S'
	AND (ES.Fecha  BETWEEN fv AND ff)
	AND (fv IS NOT NULL OR ff IS NOT NULL)
	AND (ES.Num_RegistroOF >= s OR s = 0)
	AND (ES.Num_RegistroOF <= t OR t = 0)
	-- AND (ES.Num_RegistroOF >= s OR s is Null)
	-- AND (ES.Num_RegistroOF <= t OR t is Null)
	AND ES.Estado IS NULL
	AND ES.CD_Organo_Destino IS  NULL

ORDER BY  OrgOrigen,Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_IndicesSDI_MIN;



/*************************************************************************************************/
PROCEDURE usp_RPT_Documentos_MAP(u IN VARCHAR2,o IN VARCHAR2,r IN VARCHAR2,s IN VARCHAR2, cursorOut OUT t_cursor)
IS
  ret_cursor t_cursor;
  r_ DATE := NVL(TO_DATE(r,'dd/mm/yyyy'), to_date('01/01/1900','dd/mm/yyyy'));
  s_ DATE := NVL(TO_DATE(s,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'));
  v_organismo Varchar2(255);
  v_contador number(10);

BEGIN

  select c.DS_Logo
  into v_organismo
  from Dat_Config c inner join USUARIOS uu on
       uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = u;


  SELECT COUNT(1)
  INTO v_contador
  FROM
    REGISTROES left join AUX_OFICINAS on
      REGISTROES.CD_OFICINA = AUX_OFICINAS.CD_OFICINA
  WHERE
    REGISTROES.CD_OFICINA = o
    AND TipoES = 'E'
    AND Fecha >= r_
    AND Fecha <= s_
    AND NUM_DOCUMENTOS is not null
    AND NUM_DOCUMENTOS > 0
  ORDER BY
    NUM_REGISTRO;

  IF v_contador > 0 THEN

    OPEN ret_cursor FOR
    SELECT
      NUM_REGISTROOF || ' / RG ' || NUM_REGISTRO AS NUM_REGISTRO,
      --NVL(NUM_DOCUMENTOS, 0) AS NUM_DOCUMENTOS
      NUM_DOCUMENTOS,
      AUX_OFICINAS.DS_OFICINA as OFICINA,
      v_organismo as Organismo
    FROM
      REGISTROES left join AUX_OFICINAS on
        REGISTROES.CD_OFICINA = AUX_OFICINAS.CD_OFICINA
    WHERE
      REGISTROES.CD_OFICINA = o
      AND TipoES = 'E'
     	AND Fecha >= r_
      AND Fecha <= s_
      AND NUM_DOCUMENTOS is not null
      AND NUM_DOCUMENTOS > 0
    ORDER BY
      NUM_REGISTRO;

  ELSE

     OPEN ret_cursor FOR
     SELECT
          NULL AS NUM_REGISTRO,
          NULL AS NUM_DOCUMENTOS,
          NULL AS OFICINA,
          v_organismo AS Organismo
     FROM DUAL;

  END IF;

  cursorOut:=ret_cursor;
END usp_RPT_Documentos_MAP;
-- Fin usp_RPT_Documentos_MAP

/*************************************************************************/

-- usp_RPT_Compulsas_MAP
PROCEDURE usp_RPT_Compulsas_MAP(u IN VARCHAR2,o IN VARCHAR2,r IN VARCHAR2,s IN VARCHAR2, cursorOut OUT t_cursor)
IS
  ret_cursor t_cursor;
  r_ DATE := NVL(TO_DATE(r,'dd/mm/yyyy'), to_date('01/01/1900','dd/mm/yyyy'));
  s_ DATE := NVL(TO_DATE(s,'dd/mm/yyyy'), to_date('31/12/3000','dd/mm/yyyy'));
  v_organismo Varchar2(255);
  v_contador number(10);
BEGIN

  select c.DS_Logo
  into v_organismo
  from Dat_Config c inner join USUARIOS uu on
       uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = u;

  select count(1)
  into v_contador
  FROM
    REGISTROES left join AUX_OFICINAS on
      REGISTROES.CD_OFICINA = AUX_OFICINAS.CD_OFICINA
  WHERE
    REGISTROES.CD_OFICINA = o
    AND TipoES='E'
    AND Fecha >= r_
    AND Fecha <= s_
    AND NUM_COMPULSAS is not null
    AND NUM_COMPULSAS > 0
  ORDER BY
    NUM_REGISTRO;

  IF v_contador > 0 THEN

  OPEN ret_cursor FOR
    SELECT
      NUM_REGISTROOF || ' / RG ' || NUM_REGISTRO AS NUM_REGISTRO,
      --NVL(NUM_COMPULSAS, 0) AS NUM_COMPULSAS
      NUM_COMPULSAS,
      AUX_OFICINAS.DS_OFICINA as OFICINA,
      v_organismo as Organismo
    FROM
      REGISTROES left join AUX_OFICINAS on
        REGISTROES.CD_OFICINA = AUX_OFICINAS.CD_OFICINA
    WHERE
      REGISTROES.CD_OFICINA = o
      AND TipoES='E'
     	AND Fecha >= r_
      AND Fecha <= s_
      AND NUM_COMPULSAS is not null
      AND NUM_COMPULSAS > 0
    ORDER BY
      NUM_REGISTRO;

  ELSE

    OPEN ret_cursor FOR
    SELECT
    NULL AS NUM_REGISTRO,
    NULL AS NUM_COMPULSAS,
    NULL AS OFICINA,
    v_organismo AS Organismo
    FROM DUAL;

  END IF;

  cursorOut:=ret_cursor;
END usp_RPT_Compulsas_MAP;



/*************************************************************************************************/
PROCEDURE usp_RPT_FichaReg_MAP(p IN VARCHAR2, q IN INTEGER,
                                       r IN VARCHAR2, s IN INTEGER, cursorOut OUT t_cursor)
IS
  ret_cursor t_cursor;

BEGIN

  OPEN ret_cursor FOR

      SELECT
        ES.EJERCICIO,
        ES.TIPOES,
        ES.NUM_REGISTRO,
        ES.CD_OFICINA,
        ES.NUM_REGISTROOF,
        ES.FECHA,
        ES.FECHAP,
        ES.RESUMEN AS ASUNTO,
        REL.NOMBRE,
        REL.NIF,
        OFI.DS_OFICINA,
        OFI.DIRECCION || ' ' || OFI.CP || ' ' || LOC.DS_LOCALIDAD AS DIR_OFICINA,
        LOC.DS_LOCALIDAD || ',' AS DS_LOCALIDAD,
      	TO_CHAR(SYSDATE, 'DD') || ' de ' ||
           DECODE(TO_CHAR(SYSDATE, 'MM')
           ,01, 'Enero' 	         ,02, 'Febrero' 	 ,03, 'Marzo'         ,04, 'Abril'
        	 ,05, 'Mayo'   	         ,06, 'Junio'     	 ,07, 'Julio'       	  ,08, 'Agosto'
        	 ,09, 'Septiembre'    ,10, 'Octubre'    ,11, 'Noviembre'  ,12, 'Diciembre', ' ')
        || ' de ' || TO_CHAR(SYSDATE, 'YYYY') AS FECHAHOY,
      	(select c.DS_Logo
        from Dat_Config c inner join USUARIOS uu on
          uu.CD_Organismo = c.CD_Organismo and
          uu.CD_Usuario = p)
         as ORGANISMO,
         p as USUARIO
      FROM
        REGISTROES ES
         LEFT JOIN REL_INTVSRES REL on
            ES.EJERCICIO = REL.EJERCICIO and
            ES.TIPOES = REL.TIPOES and
            ES.NUM_REGISTRO = REL.NUM_REGISTRO
         LEFT JOIN AUX_OFICINAS OFI on
            ES.CD_Oficina = OFI.CD_Oficina
         LEFT JOIN AUX_LOCALIDADES LOC on
            OFI.CD_PROVINCIA = LOC.CD_PROVINCIA and
            OFI.CD_LOCALIDAD = LOC.CD_LOCALIDAD
      WHERE
      	ES.Ejercicio = q
      	AND ES.TipoES = r
      	AND ES.Num_Registro = s;

  cursorOut:=ret_cursor;

END usp_RPT_FichaReg_MAP;




/*************************************************************************************************/
PROCEDURE usp_RPT_CertificadoReg_MAP(p IN INTEGER, q IN VARCHAR2, r IN INTEGER,
                               s IN VARCHAR2, t IN VARCHAR2, cursorOut OUT t_cursor) IS
  ret_cursor t_cursor;

BEGIN
  OPEN ret_cursor FOR

SELECT DISTINCT ES.*,
    s AS Firmante,
    DECODE(ES.TipoES,'E',
        'Remitentes:',
         'Destinatarios:') AS Desc_Interesados,
    OFI.DS_Oficina,
    OFI.Direccion,
    OFI.Telefono,
    OFI.Fax,
    OFI.CP,
    PRO.DS_Provincia,
  LOC.DS_Localidad,
    TRE.DS_TipoRegistro,
    TR.DS_Transporte,
    TRE1.DS_TipoRegistro AS DS_TipoRegOriginal,
        (CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
                RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
        THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
             TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL RG WHERE RG.Ejercicio = ES.Ejercicio AND
                RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                AND RG.Num_Registro = ES.Num_RegistroOF))
        ELSE TO_CHAR(ES.Num_RegistroOF)
        END) AS NumRegistro,
    (CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
        THEN CASE WHEN OG.CD_Organosup IS NULL
            THEN    DEP.DS_Departamento || '.' || OG.DS_Organo
            ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
            (SELECT DS_Organo FROM AUX_Organos_PROD WHERE
                OG.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
                AND OG.CD_Divisionsup = AUX_Organos_PROD.CD_Division(+)) || ')'
            END
    ELSE
        CASE WHEN ES.CD_Org_Origen IS NOT NULL
            THEN CASE WHEN OG.CD_Organosup IS NULL
                THEN OG.DS_Organo
                ELSE OG.DS_Organo || ' (' ||
         (SELECT DS_Organo FROM AUX_Organos_PROD WHERE
                    OG.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
                    AND OG.CD_Divisionsup = AUX_Organos_PROD.CD_Division(+)) || ')'
                END
        END
    END) AS OrgOrigen,
    (CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
        THEN CASE WHEN OG1.CD_Organosup IS NULL
            THEN    DEP1.DS_Departamento || '.' || OG1.DS_Organo
            ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
            (SELECT DS_Organo FROM AUX_Organos_PROD WHERE
                OG1.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
                AND OG1.CD_Divisionsup = AUX_Organos_PROD.CD_Division(+)) || ')'
            END
    ELSE
        CASE WHEN CD_Org_Destino IS NOT NULL
            THEN CASE WHEN OG1.CD_Organosup IS NULL
                THEN    OG1.DS_Organo
                ELSE  OG1.DS_Organo || ' (' ||
         (SELECT DS_Organo FROM AUX_Organos_PROD WHERE
                    OG1.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
                    AND OG1.CD_Divisionsup = AUX_Organos_PROD.CD_Division(+)) || ')'
                END
            ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
        END
    END) AS OrgDestino,
    TO_CHAR(SYSDATE, 'DD') || ' de ' ||
     DECODE(TO_CHAR(SYSDATE, 'MM'),01,'Enero'
     ,02, 'Febrero'
     ,03, 'Marzo'
     ,04, 'Abril'
     ,05, 'Mayo'
     ,06, 'Junio'
     ,07, 'Julio'
     ,08, 'Agosto'
     ,09, 'Septiembre'
     ,10, 'Octubre'
     ,11, 'Noviembre'
     ,12, 'Diciembre', ' ')
  || ' de ' || TO_CHAR(SYSDATE, 'YYYY') AS FechaHoy,
    (select c.DS_Logo
  from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and
    uu.CD_Usuario = t)
   as Organismo
FROM
  REGISTROES ES, --REL_INTvsREs IR,
    AUX_OFICINAS OFI, AUX_PROVINCIAS PRO, AUX_LOCALIDADES LOC,
    AUX_TIPOREGISTRO TRE, AUX_TIPOREGISTRO TRE1,
    AUX_TRANSPORTES TR,
    DAT_CONFIG CC, USUARIOS uuu,
    AUX_ORGANOS_PROD OG,
    AUX_ORGANOS_PROD OG1,
    AUX_DEPARTAMENTOS DEP,
    AUX_DEPARTAMENTOS DEP1
WHERE
    ES.Ejercicio = p
    AND ES.TipoES = q
    AND ES.Num_Registro = r
    --AND IR.Ejercicio=ES.Ejercicio
    --AND IR.TipoES=ES.TipoES
    --AND IR.Num_Registro=ES.Num_Registro
    AND ES.CD_Oficina = OFI.CD_Oficina
    AND (OFI.CD_Provincia = PRO.CD_Provincia(+))
    AND (OFI.CD_Provincia = LOC.CD_Provincia(+))
    AND (OFI.CD_Localidad = LOC.CD_Localidad(+))
    AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
    AND ES.TipoES = TRE.CD_TipoRegistro
    AND (ES.CD_Div_Origen = OG.CD_Division(+))
    AND (ES.CD_Org_Origen = OG.CD_Organo(+))
    AND (ES.CD_Div_Destino = OG1.CD_Division(+))
    AND (ES.CD_Org_Destino = OG1.CD_Organo(+))
    AND (ES.Tipo_Regis_Original = TRE1.CD_TipoRegistro(+))
    AND (ES.CD_Org_Origen = DEP.CD_Organo(+))
    AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
    AND (ES.CD_Org_Destino = DEP1.CD_Organo(+))
    AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
    AND UUU.CD_USUARIO = t
    AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+));

cursorOut:=ret_cursor;

END usp_RPT_CertificadoReg_MAP;

/*************************************************************************************************/
PROCEDURE usp_RPT_CertificadoReg_HIS(p IN INTEGER, q IN VARCHAR2, r IN INTEGER,
                               s IN VARCHAR2, t IN VARCHAR2, cursorOut OUT t_cursor) IS
  ret_cursor t_cursor;

BEGIN
  OPEN ret_cursor FOR

SELECT DISTINCT ES.*,
	s AS Firmante,
	DECODE(ES.TipoES,'E',
		'Remitentes:',
		 'Destinatarios:') AS Desc_Interesados,
	OFI.DS_Oficina,
	OFI.Direccion,
	OFI.Telefono,
	OFI.Fax,
	OFI.CP,
	PRO.DS_Provincia,
  LOC.DS_Localidad,
	TRE.DS_TipoRegistro,
	TR.DS_Transporte,
	TRE1.DS_TipoRegistro AS DS_TipoRegOriginal,
		(CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL_HIS RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
		THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
		     TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL_HIS RG WHERE RG.Ejercicio = ES.Ejercicio AND
				RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
				AND RG.Num_Registro = ES.Num_RegistroOF))
		ELSE TO_CHAR(ES.Num_RegistroOF)
		END) AS NumRegistro,
	(CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
		THEN CASE WHEN OG.CD_Organosup IS NULL
			THEN	DEP.DS_Departamento || '.' || OG.DS_Organo
			ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
			(SELECT DS_Organo FROM AUX_Organos_PROD WHERE
				OG.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN ES.CD_Org_Origen IS NOT NULL
			THEN CASE WHEN OG.CD_Organosup IS NULL
				THEN OG.DS_Organo
				ELSE OG.DS_Organo || ' (' ||
         (SELECT DS_Organo FROM AUX_Organos_PROD WHERE
					OG.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)) || ')'
				END
		END
	END) AS OrgOrigen,
	(CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
		THEN CASE WHEN OG1.CD_Organosup IS NULL
			THEN	DEP1.DS_Departamento || '.' || OG1.DS_Organo
			ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
			(SELECT DS_Organo FROM AUX_Organos_PROD WHERE
				OG1.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)) || ')'
			END
	ELSE
		CASE WHEN CD_Org_Destino IS NOT NULL
			THEN CASE WHEN OG1.CD_Organosup IS NULL
				THEN	OG1.DS_Organo
				ELSE  OG1.DS_Organo || ' (' ||
         (SELECT DS_Organo FROM AUX_Organos_PROD WHERE
					OG1.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)) || ')'
				END
			ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES_HIS RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
				RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
				GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
		END
	END) AS OrgDestino,
	TO_CHAR(SYSDATE, 'DD') || ' de ' ||
     DECODE(TO_CHAR(SYSDATE, 'MM'),01,'Enero'
	 ,02, 'Febrero'
	 ,03, 'Marzo'
	 ,04, 'Abril'
	 ,05, 'Mayo'
	 ,06, 'Junio'
	 ,07, 'Julio'
	 ,08, 'Agosto'
	 ,09, 'Septiembre'
	 ,10, 'Octubre'
	 ,11, 'Noviembre'
	 ,12, 'Diciembre', ' ')
  || ' de ' || TO_CHAR(SYSDATE, 'YYYY') AS FechaHoy,
	(select c.DS_Logo
  from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and
    uu.CD_Usuario = t)
   as Organismo
FROM
  REGISTROES_HIS ES, --REL_INTvsREs IR,
	AUX_OFICINAS OFI, AUX_PROVINCIAS PRO, AUX_LOCALIDADES LOC,
	AUX_TIPOREGISTRO TRE, AUX_TIPOREGISTRO TRE1,
	AUX_TRANSPORTES TR,
	DAT_CONFIG CC, USUARIOS uuu,
	AUX_ORGANOS_PROD OG,
	AUX_ORGANOS_PROD OG1,
	AUX_DEPARTAMENTOS DEP,
	AUX_DEPARTAMENTOS DEP1
WHERE
	ES.Ejercicio = p
	AND ES.TipoES = q
	AND ES.Num_Registro = r
	--AND IR.Ejercicio=ES.Ejercicio
	--AND IR.TipoES=ES.TipoES
	--AND IR.Num_Registro=ES.Num_Registro
	AND ES.CD_Oficina = OFI.CD_Oficina
	AND (OFI.CD_Provincia = PRO.CD_Provincia(+))
	AND (OFI.CD_Provincia = LOC.CD_Provincia(+))
	AND (OFI.CD_Localidad = LOC.CD_Localidad(+))
	AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
	AND ES.TipoES = TRE.CD_TipoRegistro
	AND (ES.CD_Div_Origen = OG.CD_Division(+))
	AND (ES.CD_Org_Origen = OG.CD_Organo(+))
	AND (ES.CD_Div_Destino = OG1.CD_Division(+))
	AND (ES.CD_Org_Destino = OG1.CD_Organo(+))
	AND (ES.Tipo_Regis_Original = TRE1.CD_TipoRegistro(+))
	AND (ES.CD_Org_Origen = DEP.CD_Organo(+))
	--AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
	AND (ES.CD_Org_Destino = DEP1.CD_Organo(+))
	AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
	AND UUU.CD_USUARIO = t;
	--AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+));

cursorOut:=ret_cursor;

END usp_RPT_CertificadoReg_HIS;

/*************************************************************************************************/
PROCEDURE usp_RPT_ReciboConcurso_MAP(p IN VARCHAR2, q IN VARCHAR2, r IN INTEGER,
                               s IN VARCHAR2, t IN VARCHAR2, u IN VARCHAR2, cursorOut OUT t_cursor)
IS
  -- PARAMETROS
  -- p Usuario, q Oficina, r Ejercicio Expediente,
  -- s Número Expediente, t Fecha, u Firmante
  ret_cursor t_cursor;
  v_firmante AUX_FIRMANTES.DS_FIRMANTE%type;
  v_cargo AUX_FIRMANTES.DS_CARGO%type;
  v_organismo DAT_CONFIG.DS_LOGO%type;
  v_fecha VARCHAR2(50);
  v_localidad AUX_LOCALIDADES.DS_LOCALIDAD%type;

BEGIN

  SELECT DS_FIRMANTE, DS_CARGO INTO v_firmante, v_cargo
  FROM AUX_FIRMANTES
  WHERE CD_OFICINA = q AND CD_FIRMANTE = u;

 	SELECT c.DS_Logo INTO v_organismo
  FROM DAT_CONFIG c INNER JOIN USUARIOS u ON
    c.CD_ORGANISMO = u.CD_ORGANISMO AND
    u.CD_USUARIO = p;

  SELECT NVL(LOC.DS_LOCALIDAD,'') INTO v_localidad
  FROM AUX_OFICINAS OFI, AUX_LOCALIDADES LOC
  WHERE OFI.CD_PROVINCIA = LOC.CD_PROVINCIA(+)
   AND OFI.CD_LOCALIDAD = LOC.CD_LOCALIDAD(+)
   AND OFI.CD_OFICINA = q;

  SELECT
    DECODE(TO_CHAR(TO_DATE(t,'DD/MM/YYYY'),'DD')
      ,1, 'Uno'
      ,2, 'Dos'
      ,3, 'Tres'
      ,4, 'Cuatro'
      ,5, 'Cinco'
      ,6, 'Seis'
      ,7, 'Siete'
      ,8, 'Ocho'
      ,9, 'Nueve'
      ,10, 'Diez'
      ,11, 'Once'
      ,12, 'Doce'
      ,13, 'Trece'
      ,14, 'Catorce'
      ,15, 'Quince'
      ,16, 'Dieciséis'
      ,17, 'Diecisiete'
      ,18, 'Dieciocho'
      ,19, 'Diecinueve'
      ,20, 'Veinte'
      ,21, 'Veintiuno'
      ,22, 'Veintidos'
      ,23, 'Veintitrés'
      ,24, 'Veinticuatro'
      ,25, 'Veinticinco'
      ,26, 'Veintiséis'
      ,27, 'Veintisiete'
      ,28, 'Veintiocho'
      ,29, 'Veintinueve'
      ,30, 'Treinta'
      ,31, 'Treinta y uno', '')
      || ' de ' ||
    DECODE(TO_CHAR(TO_DATE(t,'DD/MM/YYYY'),'MM')
      ,1, 'Enero'
      ,2, 'Febrero'
      ,3, 'Marzo'
      ,4, 'Abril'
      ,5, 'Mayo'
      ,6, 'Junio'
      ,7, 'Julio'
      ,8, 'Agosto'
      ,9, 'Septiembre'
      ,10, 'Octubre'
      ,11, 'Noviembre'
      ,12, 'Diciembre', '')
      || ' de ' ||
    TO_CHAR(TO_DATE(t,'DD/MM/YYYY'), 'YYYY')
  AS fecha INTO v_fecha
  FROM DUAL;

  OPEN ret_cursor FOR

    SELECT
     ES.CD_EJERCICIOEXP || ' - ' || ES.CD_EXPEDIENTE AS "Expediente",
     EXP.DS_EXPEDIENTE AS "DS_Expediente",
     ES.CD_OFICINA AS "Oficina",
     CASE
       WHEN (RG.NUM_RG) IS NOT NULL THEN
         TO_CHAR(ES.NUM_REGISTROOF) || '/RG ' || RG.NUM_RG
       ELSE
         TO_CHAR(ES.NUM_REGISTROOF)
       END AS "NumRegistro",
     ES.FECHA AS "FechaRegistro",
     v_firmante || ', ' || v_cargo AS "Firmante",
     v_fecha AS "FechaFirma",
     v_localidad AS "Poblacion",
     v_organismo as "Organismo"
    FROM RegistroES ES, REGISTROGENERAL RG, DAT_EXPEDIENTES EXP
    WHERE
     -- Joins
     ES.Ejercicio = RG.Ejercicio(+)
     AND ES.TipoES = RG.TipoES(+)
     AND ES.CD_Oficina = RG.CD_Oficina(+)
     AND ES.Num_RegistroOF = RG.Num_Registro(+)
     AND ES.CD_EJERCICIOEXP = EXP.CD_EJERCICIOEXP(+)
     AND ES.CD_EXPEDIENTE = EXP.CD_EXPEDIENTE(+)
     -- Parámetros
     AND ES.CD_EJERCICIOEXP = r
     AND ES.CD_EXPEDIENTE = s;

cursorOut:=ret_cursor;

END usp_RPT_ReciboConcurso_MAP;


/*************************************************************************************************/
END RPT;
/

prompt
prompt Creating package body RPT_HEMBLA
prompt ================================
prompt
CREATE OR REPLACE PACKAGE BODY "RPT_HEMBLA" AS


PROCEDURE usp_RPT_CertificadoReg (p IN INTEGER, q IN VARCHAR2, r IN INTEGER,
                               s IN VARCHAR2, t IN VARCHAR2, cursorOut OUT t_cursor) IS
  ret_cursor t_cursor;

BEGIN
  OPEN ret_cursor FOR

SELECT DISTINCT ES.*,
    s AS Firmante,
    DECODE(ES.TipoES,'E',
        'Remitentes:',
         'Destinatarios:') AS Desc_Interesados,
    OFI.DS_Oficina,
    OFI.Direccion,
    OFI.Telefono,
    OFI.Fax,
    OFI.CP,
    PRO.DS_Provincia,
  LOC.DS_Localidad,
    TRE.DS_TipoRegistro,
    TR.DS_Transporte,
    TRE1.DS_TipoRegistro AS DS_TipoRegOriginal,
        (CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL_HEMBLA RG WHERE RG.Ejercicio = ES.Ejercicio AND
                RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
        THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
             TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL_HEMBLA RG WHERE RG.Ejercicio = ES.Ejercicio AND
                RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                AND RG.Num_Registro = ES.Num_RegistroOF))
        ELSE TO_CHAR(ES.Num_RegistroOF)
        END) AS NumRegistro,
    (CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
        THEN CASE WHEN OG.CD_Organosup IS NULL
            THEN    DEP.DS_Departamento || '.' || OG.DS_Organo
            ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' ||
            (SELECT DS_Organo FROM AUX_Organos_PROD WHERE
                OG.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
                AND OG.CD_Divisionsup = AUX_Organos_PROD.CD_Division(+)) || ')'
            END
    ELSE
        CASE WHEN ES.CD_Org_Origen IS NOT NULL
            THEN CASE WHEN OG.CD_Organosup IS NULL
                THEN OG.DS_Organo
                ELSE OG.DS_Organo || ' (' ||
         (SELECT DS_Organo FROM AUX_Organos_PROD WHERE
                    OG.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
                    AND OG.CD_Divisionsup = AUX_Organos_PROD.CD_Division(+)) || ')'
                END
        END
    END) AS OrgOrigen,
    (CASE WHEN ES.CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
        THEN CASE WHEN OG1.CD_Organosup IS NULL
            THEN    DEP1.DS_Departamento || '.' || OG1.DS_Organo
            ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' ||
            (SELECT DS_Organo FROM AUX_Organos_PROD WHERE
                OG1.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
                AND OG1.CD_Divisionsup = AUX_Organos_PROD.CD_Division(+)) || ')'
            END
    ELSE
        CASE WHEN ES.CD_Org_Destino IS NOT NULL
            THEN CASE WHEN OG1.CD_Organosup IS NULL
                THEN    OG1.DS_Organo
                ELSE  OG1.DS_Organo || ' (' ||
         (SELECT DS_Organo FROM AUX_Organos_PROD WHERE
                    OG1.CD_Organosup = AUX_Organos_PROD.CD_Organo(+)
                    AND OG1.CD_Divisionsup = AUX_Organos_PROD.CD_Division(+)) || ')'
                END
            ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES_HEMBLA RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
        END
    END) AS OrgDestino,
    TO_CHAR(SYSDATE, 'DD') || ' de ' ||
     DECODE(TO_CHAR(SYSDATE, 'MM'),01,'Enero'
     ,02, 'Febrero'
     ,03, 'Marzo'
     ,04, 'Abril'
     ,05, 'Mayo'
     ,06, 'Junio'
     ,07, 'Julio'
     ,08, 'Agosto'
     ,09, 'Septiembre'
     ,10, 'Octubre'
     ,11, 'Noviembre'
     ,12, 'Diciembre', ' ')
  || ' de ' || TO_CHAR(SYSDATE, 'YYYY') AS FechaHoy,
    (select c.DS_Logo
  from Dat_Config c inner join USUARIOS uu on
    uu.CD_Organismo = c.CD_Organismo and
    uu.CD_Usuario = t)
   as Organismo
FROM
  REGISTROES_HEMBLA ES,
    AUX_OFICINAS OFI, AUX_PROVINCIAS PRO, AUX_LOCALIDADES LOC,
    AUX_TIPOREGISTRO TRE, AUX_TIPOREGISTRO TRE1,
    AUX_TRANSPORTES TR,
    DAT_CONFIG CC, USUARIOS uuu,
    AUX_ORGANOS_PROD OG,
    AUX_ORGANOS_PROD OG1,
    AUX_DEPARTAMENTOS DEP,
    AUX_DEPARTAMENTOS DEP1
WHERE
    ES.Ejercicio = p
    AND ES.TipoES = q
    AND ES.Num_Registro = r
    AND ES.CD_Oficina = OFI.CD_Oficina
    AND (OFI.CD_Provincia = PRO.CD_Provincia(+))
    AND (OFI.CD_Provincia = LOC.CD_Provincia(+))
    AND (OFI.CD_Localidad = LOC.CD_Localidad(+))
    AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
    AND ES.TipoES = TRE.CD_TipoRegistro
    AND (ES.CD_Div_Origen = OG.CD_Division(+))
    AND (ES.CD_Org_Origen = OG.CD_Organo(+))
    AND (ES.CD_Div_Destino = OG1.CD_Division(+))
    AND (ES.CD_Org_Destino = OG1.CD_Organo(+))
    AND (ES.Tipo_Regis_Original = TRE1.CD_TipoRegistro(+))
    AND (ES.CD_Org_Origen = DEP.CD_Organo(+))
    AND (ES.CD_Dep_Origen=DEP.CD_Departamento(+))
    AND (ES.CD_Org_Destino = DEP1.CD_Organo(+))
    AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
    AND UUU.CD_USUARIO = t
    AND (ES.CD_Dep_Destino=DEP1.CD_Departamento(+));

cursorOut:=ret_cursor;

END usp_RPT_CertificadoReg;

PROCEDURE usp_RPT_FichaReg (p IN VARCHAR2,
                            q IN INTEGER,
                            r IN VARCHAR2,
                            s IN INTEGER,
                            cursorOut OUT t_cursor)
IS
  ret_cursor t_cursor;

BEGIN

OPEN ret_cursor FOR

SELECT ES.EJERCICIO,
        ES.TIPOES,
        ES.NUM_REGISTRO,
        ES.CD_OFICINA,
        ES.NUM_REGISTROOF,
        ES.FECHA,
        ES.FECHAP,
        ES.RESUMEN AS ASUNTO,
        REL.NOMBRE,
        REL.NIF,
        OFI.DS_OFICINA,
        OFI.DIRECCION || ' ' || OFI.CP || ' ' || LOC.DS_LOCALIDAD AS DIR_OFICINA,
        LOC.DS_LOCALIDAD || ',' AS DS_LOCALIDAD,
      	TO_CHAR(SYSDATE, 'DD') || ' de ' ||
           DECODE(TO_CHAR(SYSDATE, 'MM')
           ,01, 'Enero' 	         ,02, 'Febrero' 	 ,03, 'Marzo'         ,04, 'Abril'
        	 ,05, 'Mayo'   	         ,06, 'Junio'     	 ,07, 'Julio'       	  ,08, 'Agosto'
        	 ,09, 'Septiembre'    ,10, 'Octubre'    ,11, 'Noviembre'  ,12, 'Diciembre', ' ')
        || ' de ' || TO_CHAR(SYSDATE, 'YYYY') AS FECHAHOY,
      	(select c.DS_Logo
        from Dat_Config c inner join USUARIOS uu on
          uu.CD_Organismo = c.CD_Organismo and
          uu.CD_Usuario = p)
         as ORGANISMO,
         p as USUARIO
FROM REGISTROES_HEMBLA ES
LEFT JOIN REL_INTVSRES_HEMBLA REL ON ES.EJERCICIO = REL.EJERCICIO
                                 AND ES.TIPOES = REL.TIPOES
                                 AND ES.NUM_REGISTRO = REL.NUM_REGISTRO
LEFT JOIN AUX_OFICINAS OFI ON  ES.CD_Oficina = OFI.CD_Oficina
LEFT JOIN AUX_LOCALIDADES LOC ON OFI.CD_PROVINCIA = LOC.CD_PROVINCIA
                             AND OFI.CD_LOCALIDAD = LOC.CD_LOCALIDAD
WHERE	ES.Ejercicio = q
  AND ES.TipoES = r
  AND ES.Num_Registro = s;

  cursorOut:=ret_cursor;

END usp_RPT_FichaReg;

PROCEDURE USP_RPT_INDICESE_SELEC (p IN VARCHAR2,
                                  q IN VARCHAR2,
                                  cursorOut OUT t_cursor)
--p =  usuario
--q = oficina

IS
  ret_cursor t_cursor;


BEGIN


  OPEN ret_cursor FOR

SELECT DISTINCT

  ES.Num_RegistroOF,
  (CASE
   WHEN (SELECT Num_RG FROM REGISTROGENERAL_HEMBLA RG WHERE RG.Ejercicio = ES.Ejercicio AND
                RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
    THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
        TO_CHAR((SELECT Num_RG FROM REGISTROGENERAL_HEMBLA RG WHERE RG.Ejercicio = ES.Ejercicio AND
                RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                AND RG.Num_Registro = ES.Num_RegistroOF))
    ELSE TO_CHAR(ES.Num_RegistroOF)
  END) AS NumRegistro,
  OFI.DS_Oficina,
  ES.FechaP AS Fecha,

  (CASE
   WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
     THEN CASE
            WHEN OG.CD_Organosup IS NULL
              THEN DEP.DS_Departamento || '.' || OG.DS_Organo ||
                   NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES_HEMBLA RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                         RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
               ELSE DEP.DS_Departamento || '.' || OG.DS_Organo ||
                    ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                             WHERE OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                              AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                   NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES_HEMBLA RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                         RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
          END
     ELSE
           CASE
            WHEN ES.CD_Org_Origen IS NOT NULL
              THEN
                CASE WHEN OG.CD_Organosup IS NULL
                  THEN OG.DS_Organo ||
                       NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES_HEMBLA RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                             RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                  ELSE OG.DS_Organo || ' (' || (SELECT DS_Organo FROM AUX_ORGANOS_PROD
                                                WHERE  OG.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
                                                 AND OG.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
                       NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES_HEMBLA RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                             RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                            GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                  END
              ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES_HEMBLA RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                    RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
          END
     END) AS OrgOrigen,

  ES.Cd_Div_Destino,

  (CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
    THEN CASE WHEN OG1.CD_Organosup IS NULL
      THEN DEP1.DS_Departamento || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo ||
      DECODE (OG1.CD_Oficina, NULL,'', ' - (' || OFI2.DS_Oficina || ')')
      ELSE DEP1.DS_Departamento || '. ' || ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
       (SELECT DS_Organo FROM AUX_ORGANOS_PROD
        WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
         AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+)) || ')' ||
         DECODE (OG1.CD_Oficina, NULL,'', ' - (' || OFI2.DS_Oficina || ')')
      END
  ELSE
    CASE WHEN CD_Org_Destino IS NOT NULL
      THEN CASE WHEN OG1.CD_Organosup IS NULL
           THEN ES.CD_Org_Destino || ' ' || OG1.DS_Organo ||
           DECODE (OG1.CD_Oficina, NULL,'', ' - (' || OFI2.DS_Oficina || ')')
           ELSE ES.CD_Org_Destino || ' ' || OG1.DS_Organo || ' (' ||
              (SELECT DS_Organo FROM AUX_ORGANOS_PROD
              WHERE OG1.CD_Organosup = AUX_ORGANOS_PROD.CD_Organo(+)
              AND OG1.CD_DIVISIONSUP = AUX_ORGANOS_PROD.CD_DIVISION(+) ) || ')' ||
              DECODE (OG1.CD_Oficina, NULL,'', ' - (' || OFI2.DS_Oficina || ')')
           END
      ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES_HEMBLA RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
      END
  END) AS OrgDestino,

    (select rel1.direccion || ' ' || rel1.cp || ' ' || loc1.ds_localidad
    from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
    where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
     and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
     and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as DireccionOrgDestino,

  (select loc1.cd_provincia
    from REL_ORGVSLOC rel1, AUX_LOCALIDADES loc1
    where rel1.cd_provincia = loc1.cd_provincia and rel1.cd_localidad = loc1.cd_localidad
    and rel1.cd_provincia = CD_Prov_Destino and rel1.cd_localidad = CD_Loc_Destino
    and rel1.cd_division = CD_Div_Destino and rel1.cd_organo = CD_Org_Destino) as CodigoProvincia,

  ES.Resumen,
  OFI.Direccion || ' ' ||
  OFI.CP || ' ' ||
  (select loc2.DS_Localidad from AUX_LOCALIDADES loc2
  where loc2.CD_Provincia = OFI.CD_PROVINCIA and loc2.CD_Localidad = OFI.Cd_Localidad) as DireccionOficina,

  (select c.DS_Logo
   from Dat_Config c inner join USUARIOS uu on
   uu.CD_Organismo = c.CD_Organismo and uu.CD_Usuario = p) as Organismo

FROM
  AUX_OFICINAS OFI,
  AUX_OFICINAS OFI2,
  REGISTROES_HEMBLA ES,
  AUX_ORGANOS_PROD OG,
  AUX_ORGANOS_PROD OG1,
  AUX_DEPARTAMENTOS DEP,
  AUX_DEPARTAMENTOS DEP1,
  TEMP_LISTADO_REG_ENT TEMP

WHERE
  TEMP.EJERCICIO = ES.EJERCICIO
  AND TEMP.TIPOES = ES.TIPOES
  AND TEMP.NUM_REGISTRO = ES.NUM_REGISTRO
  AND TEMP.CD_USUARIO = p
  AND TEMP.CD_OFICINA = q
  AND ES.CD_OFICINA = OFI.CD_OFICINA
  AND ES.CD_Div_Origen=OG.CD_Division(+)
  AND ES.CD_Org_Origen=OG.CD_Organo(+)
  AND ES.CD_Org_Origen=DEP.CD_Organo(+)
  AND ES.CD_Dep_Origen=DEP.CD_Departamento(+)
  AND ES.CD_Org_Destino=DEP1.CD_Organo(+)
  AND ES.CD_Dep_Destino=DEP1.CD_Departamento(+)
  AND ES.CD_Div_Destino = OG1.CD_Division (+)
  AND ES.CD_Org_Destino = OG1.CD_Organo (+)
  AND OG1.CD_Oficina = OFI2.CD_Oficina (+)


ORDER BY  OrgDestino, Num_RegistroOF;

  cursorOut:=ret_cursor;


END USP_RPT_INDICESE_SELEC;

PROCEDURE usp_RPT_Interesadosrecibo (p IN INTEGER,
                                     q IN VARCHAR2,
                                     r IN INTEGER,
                                     cursorOut OUT t_cursor)
IS
  ret_cursor t_cursor;

BEGIN
  OPEN ret_cursor FOR

      SELECT *
      FROM REL_INTVSRES_HEMBLA RINT
      WHERE RINT.EJERCICIO = p
      AND RINT.TIPOES = q
      AND RINT.NUM_REGISTRO = r;

  cursorOut:=ret_cursor;

END usp_RPT_Interesadosrecibo;

PROCEDURE usp_RPT_Registro_fechas(p IN VARCHAR2, q IN VARCHAR2, r IN NUMBER,
kk IN VARCHAR2, cc IN VARCHAR2,x IN VARCHAR2, v IN VARCHAR2, w IN VARCHAR2, P_TipoAsunto IN INTEGER,
cursorOut OUT t_cursor) IS


  ret_cursor t_cursor;

fv DATE := TO_DATE(kk, 'DD/MM/YYYY');
ff DATE := TO_DATE(cc, 'DD/MM/YYYY');

hay_trans INTEGER;
ds_trans VARCHAR2(60);
ds_trans1 VARCHAR2(60);
ds_trans2 VARCHAR2(60);

BEGIN

      BEGIN
          SELECT DS_TRANSPORTE INTO ds_trans1
          FROM AUX_TRANSPORTES WHERE CD_TRANSPORTE = v;
      EXCEPTION
          WHEN NO_DATA_FOUND
              THEN ds_trans1 := NULL;
      END;

      BEGIN
          SELECT DS_TRANSPORTE INTO ds_trans2
          FROM AUX_TRANSPORTES WHERE CD_TRANSPORTE = w;
      EXCEPTION
          WHEN NO_DATA_FOUND
              THEN ds_trans2 := NULL;
      END;

      IF v IS NULL AND w IS NULL THEN
            hay_trans := 0;
            ds_trans := NULL;
      ELSE
            hay_trans := 1;
            IF v IS NULL THEN
                ds_trans := ds_trans2;
            ELSE
                IF w IS NULL THEN
                    ds_trans := ds_trans1;
                ELSE
                    ds_trans := ds_trans1 || ' / ' || ds_trans2;
                END IF;
            END IF;
      END IF;


  OPEN ret_cursor FOR

SELECT DISTINCT
    fv AS fechai, ff AS fechaf,
    ES.Num_RegistroOF,
    (CASE WHEN (SELECT Num_RG FROM REGISTROGENERAL_HEMBLA RG WHERE RG.Ejercicio = ES.Ejercicio AND
                RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                AND RG.Num_Registro = ES.Num_RegistroOF) IS NOT NULL
        THEN TO_CHAR(ES.Num_RegistroOF)  || '/RG ' ||
             TO_CHAR((SELECT Num_RG FROM Registrogeneral_Hembla RG WHERE RG.Ejercicio = ES.Ejercicio AND
                RG.TipoES = ES.TipoES AND RG.CD_Oficina = ES.CD_Oficina
                AND RG.Num_Registro = ES.Num_RegistroOF))
        ELSE TO_CHAR(ES.Num_RegistroOF)
        END) AS NumRegistro,
    OFI.DS_Oficina,
    ES.FechaP AS Fecha ,
    DECODE(ES.TipoES, 'E', 'Entrada','S', 'Salida', '') AS TipoES,
    US.DS_Usuario,
    (CASE WHEN ES.CD_Org_Origen IS NOT NULL AND CD_Dep_Origen IS NOT NULL
        THEN CASE WHEN OG.CD_Organosup IS NULL
            THEN    DEP.DS_Departamento || '.' || OG.DS_Organo ||
                CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' ||
                    MAX(Nombre) FROM REL_INTVSRES_HEMBLA RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                    RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                ELSE ' '
                END
            ELSE  DEP.DS_Departamento || '.' || OG.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
                OG.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG.CD_Organosup= VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
                CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES_HEMBLA RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                    RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                ELSE ' '
                END
            END
    ELSE
        CASE WHEN ES.CD_Org_Origen IS NOT NULL
            THEN CASE WHEN OG.CD_Organosup IS NULL
                THEN    OG.DS_Organo ||
                    CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES_HEMBLA RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                    ELSE ' '
                    END
                ELSE  OG.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
                    OG.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG.CD_Organosup=VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
                    CASE WHEN TipoES = 'E' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES_HEMBLA RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                    ELSE ' '
                    END
                END
            ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES_HEMBLA RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
        END
    END) AS OrgOrigen,
    (CASE WHEN CD_Org_Destino IS NOT NULL AND CD_Dep_Destino IS NOT NULL
        THEN CASE WHEN OG1.CD_Organosup IS NULL
            THEN    DEP1.DS_Departamento || '.' || OG1.DS_Organo ||
                CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES_HEMBLA RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                    RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                ELSE ' '
                END
            ELSE  DEP1.DS_Departamento || '.' || OG1.DS_Organo || ' (' || (SELECT  VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
                OG1.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG1.CD_Organosup= VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
                CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES_HEMBLA RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                    RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                    GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                ELSE ' '
                END
            END
    ELSE
        CASE WHEN CD_Org_Destino IS NOT NULL
            THEN CASE WHEN OG1.CD_Organosup IS NULL
                THEN    OG1.DS_Organo ||
                    CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES_HEMBLA RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                    ELSE ' '
                    END
                ELSE  OG1.DS_Organo || ' (' || (SELECT VS_ORGANOS_PROD3.DS_Organo FROM VS_ORGANOS_PROD3 WHERE
                    OG1.CD_Divisionsup= VS_ORGANOS_PROD3.CD_Division AND OG1.CD_Organosup=VS_ORGANOS_PROD3.CD_Organo(+)) || ')' ||
                    CASE WHEN TipoES = 'S' THEN NVL((SELECT ' / ' || MAX(Nombre) FROM REL_INTVSRES_HEMBLA RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                        RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                        GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro), ' ')
                    ELSE ' '
                    END
                END
            ELSE (SELECT MAX(Nombre) FROM REL_INTVSRES_HEMBLA RINT WHERE RINT.Ejercicio = ES.Ejercicio AND
                RINT.TipoES = ES.TipoES AND RINT.Num_Registro = ES.Num_Registro
                GROUP BY RINT.Ejercicio, RINT.TipoES, RINT.Num_Registro)
        END
    END) AS OrgDestino,
    ' /' || ES.CD_Regis_Original AS CD_Regis_Original,
    DECODE(ES.Tipo_Regis_Original,'E', ' / Entrada', 'S', ' / Salida', '') AS Tipo_Regis_original,
    ES.Num_Regis_Original,
    ES.Fecha_Regis_Original,
    ES.Tipo_Transporte,
    TR.DS_Transporte,
    ES.Num_Transporte,
    NVL(CM.CONTENIDO,'') || ' ' || ES.Resumen AS Resumen,
    ES.Estado,
    (SELECT c.DS_Logo
     FROM Dat_Config c
     INNER JOIN USUARIOS uu ON uu.CD_Organismo = c.CD_Organismo
                           AND uu.CD_Usuario = x) AS Organismo,
   ES.NUM_DOCUMENTOS,
   hay_trans as FILTRO_TRANSPORTE,
   ds_trans as DS_FILTRO_TRANSPORTE,
    CASE WHEN P_TipoAsunto IS NOT NULL
       THEN CM.DS_COMENTARIO
       ELSE ''
    END AS TipoAsunto

FROM AUX_OFICINAS OFI,
     REGISTROES_HEMBLA ES,
     AUX_ORGANOS_PROD OG,
     AUX_ORGANOS_PROD OG1,
     AUX_TRANSPORTES TR,
     UsuariosV1 US,
     DAT_CONFIG CC,
     USUARIOS uuu,
    AUX_DEPARTAMENTOS DEP,
    AUX_DEPARTAMENTOS DEP1,
    AUX_COMENTARIOS CM
WHERE
    ES.CD_Oficina = OFI.CD_Oficina
    AND (ES.CD_DIV_ORIGEN =OG.CD_Division(+))
    AND (ES.CD_ORG_ORIGEN =OG.CD_Organo(+))
    AND (ES.CD_ORG_ORIGEN =DEP.CD_Organo(+))
    AND (ES.CD_Dep_Origen =DEP.CD_Departamento(+))
    AND (ES.CD_ORG_DESTINO=DEP1.CD_Organo(+))
    AND (ES.CD_Dep_Destino =DEP1.CD_Departamento(+))
    AND (ES.CD_DIV_DESTINO=OG1.CD_Division(+))
    AND (ES.CD_ORG_DESTINO =OG1.CD_Organo(+))
    AND (ES.Tipo_Transporte=TR.CD_Transporte(+))
    AND (ES.CD_OFICINA = CM.CD_OFICINA(+))
    AND (ES.CD_TIPOASUNTO_COM = CM.CD_COMENTARIO(+))
    AND ES.Usuario = US.DNI
    AND OFI.CD_Oficina = p
    AND ES.TipoES= q
    AND UUU.CD_ORGANISMO = CC.CD_ORGANISMO
    AND UUU.CD_USUARIO = x
    AND (ES.Fecha >= fv  AND ES.Fecha <= ff)
    AND (ES.Num_RegistroOF = r OR r = 0)
    AND ((ES.TIPO_TRANSPORTE IN (v,w)) OR hay_trans = 0)
    AND (ES.CD_TIPOASUNTO_COM = P_TipoAsunto OR P_TipoAsunto is null OR P_TipoAsunto = 0)


ORDER BY  ES.Num_RegistroOF;

  cursorOut:=ret_cursor;
END usp_RPT_Registro_fechas;

END RPT_HEMBLA;
/


spool off

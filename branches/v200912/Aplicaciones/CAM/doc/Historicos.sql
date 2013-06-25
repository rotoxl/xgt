-- Add/modify columns 
alter table REGISTRODOC_HEMBLA add RELACIONADO number(1);
-- Add comments to the columns 
comment on column REGISTRODOC_HEMBLA.RELACIONADO
  is 'Documento relacionado';




CREATE OR REPLACE PROCEDURE USP_CARGA_HISTORICOS_EMBLA
 ---Version Embla PROC-3.182.225951
         (
         P_EJERCICIO NUMBER,
         P_FECHADESDE DATE,
         P_FECHAHASTA DATE,
         P_VALORCOMMIT NUMBER,
         P_SALIDA OUT VARCHAR2)
 
         AS
 
         V_SALIDA VARCHAR2(500):= 'OK';
         V_FECHADESDE DATE:= TO_DATE(TO_CHAR(P_FECHADESDE, 'DD/MM/YYYY'),'DD/MM/YYYY');
         V_FECHAHASTA DATE:= TO_DATE(TO_CHAR(P_FECHAHASTA, 'DD/MM/YYYY'),'DD/MM/YYYY');
         V_VALORCOMMIT NUMBER(10):= P_VALORCOMMIT;
         V_NUM_REGISTRO REGISTROES.NUM_REGISTRO%TYPE;
 
         BEGIN
 
         IF P_VALORCOMMIT IS NULL THEN
            V_VALORCOMMIT:= 1000;
         END IF;
 
          DECLARE
          CURSOR C1 IS  -- USAMOS UN CURSOR PARA INSERTAR LOS REGISTROS EN LAS TABLAS DE HISTÓRICOS SEGÚN LAS FECHAS INDICADAS
             SELECT *
             FROM REGISTROES
             WHERE EJERCICIO = P_EJERCICIO
             AND FECHA BETWEEN V_FECHADESDE AND V_FECHAHASTA;
          BEGIN
          FOR R1 IN C1 LOOP
            INSERT INTO REGISTROES_HEMBLA -- INSERTAMOS EN REGISTROES_HEMBLA LOS REGISTROS DE REGISTROES QUE ESTÁN EN EL CURSOR
            (ejercicio, tipoes, num_registro, fecha, fechap, cd_oficina, num_registroof, usuario, cd_division_destino,
             cd_organo_destino, cd_division_origen, cd_organo_origen, cd_dep_origen, ejercicio_regis_original,
             cd_regis_original, tipo_regis_original, num_regis_original, fecha_regis_original, tipo_transporte,
             num_transporte, resumen, descriptores, unidad_registral, cd_dep_destino, persona, fechau, cd_ejercicioexp,
             cd_expediente, cd_tipo, oferta, expoferta, estado, asunto, usuariou, observaciones, num_sobres,
             nacext, embajada, cd_tipodocumento, fecha_presen, cd_loc_origen, cd_prov_origen, cd_div_origen,
             cd_org_origen, cd_loc_destino, cd_prov_destino, cd_div_destino, cd_org_destino, num_documentos,
             num_compulsas, num_rec, num_recepcion, resumen_inter, cd_estado, descripcion, obs, cd_tipoasunto_com,
             id_registro_sir, fecha_presen_sh,DS_OFICINA_ORIGINAL,EJERCICIO_REL,TIPOES_REL,NUM_REGISTRO_REL)
            VALUES
            (R1.ejercicio, R1.tipoes, R1.num_registro, R1.fecha, R1.fechap, R1.cd_oficina, R1.num_registroof,
             R1.usuario, R1.cd_division_destino, R1.cd_organo_destino, R1.cd_division_origen, R1.cd_organo_origen,
             R1.cd_dep_origen, R1.ejercicio_regis_original, R1.cd_regis_original, R1.tipo_regis_original,
             R1.num_regis_original, R1.fecha_regis_original, R1.tipo_transporte, R1.num_transporte, R1.resumen,
             R1.descriptores, R1.unidad_registral, R1.cd_dep_destino, R1.persona, R1.fechau, R1.cd_ejercicioexp,
             R1.cd_expediente, R1.cd_tipo, R1.oferta, R1.expoferta, R1.estado, R1.asunto, R1.usuariou, R1.observaciones,
             R1.num_sobres, R1.nacext, R1.embajada, R1.cd_tipodocumento, R1.fecha_presen, R1.cd_loc_origen,
             R1.cd_prov_origen, R1.cd_div_origen, R1.cd_org_origen, R1.cd_loc_destino, R1.cd_prov_destino,
             R1.cd_div_destino, R1.cd_org_destino, R1.num_documentos, R1.num_compulsas, R1.num_rec, R1.num_recepcion,
             R1.resumen_inter, R1.cd_estado, R1.descripcion, R1.obs, R1.cd_tipoasunto_com, R1.id_registro_sir,
             R1.fecha_presen_sh,R1.DS_OFICINA_ORIGINAL,R1.EJERCICIO_REL,R1.TIPOES_REL,R1.NUM_REGISTRO_REL);
 
            V_NUM_REGISTRO:= R1.NUM_REGISTRO;
 
            INSERT INTO REGISTROESTRA_HEMBLA -- INSERTAMOS EN LA TABLA DE TRAMITACIÓN DE HISTÓRICOS EN FUNCIÓN DEL CURSOR
            (ejercicio,
            tipoes,
            num_registro,
            f_pendiente,
            cd_camino,
            cd_tramite,
            cd_usuario_pendiente,
            f_ejecucion,
            cd_usuario_ejecucion,
            f_efectos,
            f_vencimiento,
            comentario,
            infoejec,
            critico,
            cd_oficina,
            cd_organo,
            cd_departamento,
            firmante1,
            firmante2,
            cd_motivor,
            cd_division,
            devuelto,
            fecha_sh)
 
            SELECT ejercicio,
                    tipoes,
                    num_registro,
                    f_pendiente,
                    cd_camino,
                    cd_tramite,
                    cd_usuario_pendiente,
                    f_ejecucion,
                    cd_usuario_ejecucion,
                    f_efectos,
                    f_vencimiento,
                    comentario,
                    infoejec,
                    critico,
                    cd_oficina,
                    cd_organo,
                    cd_departamento,
                    firmante1,
                    firmante2,
                    cd_motivor,
                    cd_division,
                    devuelto,
                    fecha_sh
 
            FROM REGISTROESTRA
            WHERE EJERCICIO = R1.EJERCICIO
            AND TIPOES = R1.TIPOES
            AND NUM_REGISTRO = R1.NUM_REGISTRO;
 
            INSERT INTO REGISTROGENERAL_HEMBLA -- INSERTAMOS EN LA TABLA DE REGISTRO GENERAL DE HISTÓRICOS EN FUNCIÓN DEL CURSOR
            (ejercicio,
            tipoes,
            num_rg,
            cd_oficina,
            num_registro)
 
            SELECT ejercicio,
                  tipoes,
                  num_rg,
                  cd_oficina,
                  num_registro
            FROM REGISTROGENERAL
            WHERE EJERCICIO = R1.EJERCICIO
            AND TIPOES = R1.TIPOES
            AND NUM_REGISTRO = R1.NUM_REGISTROOF
            AND CD_OFICINA = R1.CD_OFICINA;
 
            INSERT INTO REL_INTVSRES_HEMBLA -- INSERTAMOS EN LA TABLA DE INTERESADOS DE HISTÓRICOS EN FUNCIÓN DEL CURSOR
            (ejercicio,
            tipoes,
            num_registro,
            cd_interesado,
            nombre,
            nif,
            localidad,
            provincia,
            pais,
            provincia_ext,
            cp,
            f_nacimiento,
            direccion_postal)
            SELECT ejercicio,
                  tipoes,
                  num_registro,
                  cd_interesado,
                  nombre,
                  nif,
                  localidad,
                  provincia,
                  pais,
                  provincia_ext,
                  cp,
                  f_nacimiento,
                  direccion_postal
            FROM REL_INTVSRES
            WHERE EJERCICIO = R1.EJERCICIO
            AND TIPOES = R1.TIPOES
            AND NUM_REGISTRO = R1.NUM_REGISTRO;
 
            INSERT INTO REGISTRODOC_HEMBLA -- INSERTAMOS EN LA TABLA DE DOCUMENTOS DE HISTÓRICOS EN FUNCIÓN DEL CURSOR
            (ejercicio,
            tipoes,
            num_registro,
            num_documento,
            descripcion,
            fecha_informe,
            direccion_url,
            ruta,
            fichero,
            autor,
            tipo,
            relacionado)
            SELECT ejercicio,
                  tipoes,
                  num_registro,
                  num_documento,
                  descripcion,
                  fecha_informe,
                  direccion_url,
                  ruta,
                  fichero,
                  autor,
                  tipo,
                  relacionado
            FROM REGISTRODOC
            WHERE EJERCICIO = R1.EJERCICIO
            AND TIPOES = R1.TIPOES
            AND NUM_REGISTRO = R1.NUM_REGISTRO;
 
            DELETE FROM REGISTRODOC -- BORRAMOS LO DOCUMENTOS EN FUNCIÓN DEL CURSOR
            WHERE EJERCICIO = R1.EJERCICIO
            AND TIPOES = R1.TIPOES
            AND NUM_REGISTRO = R1.NUM_REGISTRO;
 
            DELETE FROM REL_INTVSRES -- BORRAMOS LOS INTERESADOS EN FUNCIÓN DEL CURSOR
            WHERE EJERCICIO = R1.EJERCICIO
            AND TIPOES = R1.TIPOES
            AND NUM_REGISTRO = R1.NUM_REGISTRO;
 
            DELETE FROM REGISTROGENERAL -- BORRAMOS LOS REGISTROS GENERALES EN FUNCIÓN DEL CURSOR
            WHERE EJERCICIO = R1.EJERCICIO
            AND TIPOES = R1.TIPOES
            AND NUM_REGISTRO = R1.NUM_REGISTROOF
            AND CD_OFICINA = R1.CD_OFICINA;
 
            DELETE FROM REGISTROESTRA -- BORRAMOS LA TABLA DE TRAMITACIÓN EN FUNCIÓN DEL CURSOR
            WHERE EJERCICIO = R1.EJERCICIO
            AND TIPOES = R1.TIPOES
            AND NUM_REGISTRO = R1.NUM_REGISTRO;
 
            DELETE FROM REGISTROES -- BORRAMOS LA TABLA DE REGISTRO EN FUNCIÓN DEL CURSOR
            WHERE EJERCICIO = R1.EJERCICIO
            AND TIPOES = R1.TIPOES
            AND NUM_REGISTRO = R1.NUM_REGISTRO;
 
            IF MOD(C1%ROWCOUNT,V_VALORCOMMIT) = 0 THEN
              COMMIT;
            END IF;
          END LOOP;
          END;
 
         P_SALIDA := V_SALIDA;
 
         EXCEPTION
         WHEN OTHERS THEN
         BEGIN
            ROLLBACK;
            DBMS_OUTPUT.put_line(SQLERRM);
            P_SALIDA := SQLERRM||TO_CHAR(V_NUM_REGISTRO);
         END;
 
 
    END USP_CARGA_HISTORICOS_EMBLA; 

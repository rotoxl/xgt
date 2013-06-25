#!/bin/sh


echo ""
# INSTRUCCIONES
#	* crear carpeta Extensores (OJO A LAS MAYÚSCULAS)
#	* (sólo la primera vez, después este script se encarga de actulizarlos) obtener ahí cada uno de los proyectos requeridos. Ejemplo:
#			svn checkout "https://svn.burke.net/svn/svnProductos/Gotta.Java/trunk/extensores/Lawson" Lawson"
#       * volver al contexto
#	* ejecutar este script

SOURCE="${BASH_SOURCE[0]}"
while [ -h "$SOURCE" ] ; do SOURCE="$(readlink "$SOURCE")"; done
gotta="$( cd -P "$( dirname "$SOURCE" )" && pwd )"

clear

# colores
colorOff='\e[0m'       # Text Reset
red='\e[0;31m'          # Red
green='\e[0;32m'        # Green
yellow='\e[0;33m'       # Yellow



##### Mostramos dir actual
echo -e "${green}$gotta"
tput sgr0


##### Actualizamos extensores
echo  -e "${green}>>> Actualizamos extensores"
cd Extensores
for dir in `ls "$gotta/Extensores/"`
	do
	#~ echo "$gotta/$dir"
	  #~ if [ -d "$gotta/$dir" ]; then
		#~ do
			echo -e "${yellow}>>> >>> $dir"
			tput sgr0
				cd "$dir"
				svn up
				cd ..
			echo "-------------------------------------"
			echo ""
		#~ done
	  #~ fi
	done
cd ..

##### Actualizamos gotta
echo -e "${green}>>> Actualizamos gotta"
tput sgr0
	svn up
	echo "-------------------------------------"
	echo ""


##### 
echo -e "${green}>>> Copiamos los archivos de los extensores a gotta"
tput sgr0
echo ""
	cp Extensores/*/WEB-INF/src/es/burke/misc/*	  		WEB-INF/src/es/burke/misc/
	cp Extensores/*/WEB-INF/src/es/burke/gotta/*.java 	 	WEB-INF/src/es/burke/gotta/
	cp Extensores/*/WEB-INF/src/es/burke/gotta/dll/*.java 	WEB-INF/src/es/burke/gotta/dll/
	cp Extensores/*/WEB-INF/lib/*						WEB-INF/lib/
echo "-------------------------------------"
echo ""


##### 
echo -e "${green}>>> Compilamos todo"
tput sgr0
echo ""
ant
echo "-------------------------------------"
echo ""

echo -e "${green}>>> Terminado con éxito, no olvides recargar el contexto en probe"
tput sgr0
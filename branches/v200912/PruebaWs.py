from javax.servlet.http import HttpServlet
from SOAPpy import WSDL, SOAPConfig
config=SOAPConfig(debug=False)
p=WSDL.Proxy('http://localhost:8180/gotta/soap.py?wsdl',config=config)

class PruebaWs2(HttpServlet):
    def doGet(self, request, res):
        res.setContentType("text/plain")
        out = res.getOutputStream()
        ret=p.listaAplicaciones()
        print ret
        for i in ret:
            print>>out, i
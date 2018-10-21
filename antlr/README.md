## How-to generate the certificate for let's encrypt

openssl pkcs12 -export -out certificate.pfx -inkey privkey.pem -in cert.pem -certfile chain.pem


## Sign jar files manually

jarsigner -storetype pkcs12 -keystore /e/Github/certificate.pfx -tsa http://zeitstempel.dfn.de org.antlr.antlr4-runtime_4.7.1.jar 1


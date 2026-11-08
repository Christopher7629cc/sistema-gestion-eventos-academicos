# Subida del Avance #2 a GitHub

El repositorio original ya contiene `src/main/java/utp/eventos/domain/` con 10 archivos Java básicos, `App.java`, `IOUtils.java`, el README y los materiales del Avance #1.

Esta carpeta **ya está adaptada** a la misma estructura. No subas la carpeta contenedora `Avance2_Compatible_Repositorio` como una subcarpeta nueva. El contenido se integra en la raíz del repositorio.

1. Conservar `src/main/java/utp/eventos/utils/IOUtils.java` y `docs/Tablas-semestral.docx` existentes.
2. Reemplazar los 10 archivos en `src/main/java/utp/eventos/domain/` por los diez archivos del paquete.
3. Reemplazar `src/main/java/utp/eventos/App.java` con la demostración del paquete.
4. Agregar `docs/avance2/` y los dos archivos `Diagrama_UML_actualizado` en `docs/uml/`.
5. Actualizar `README.md` y agregar `.gitignore`.
6. Comprobar que los archivos nuevos aparecen en la rama principal. Crear un commit descriptivo: `Avance 2: implementar clases Java y actualizar UML y tablas`.
7. Verificar que el docente sea colaborador en GitHub: Settings > Collaborators (según interfaz).

Comandos para ejecutar desde la raíz (JDK 17+):

```
javac -encoding UTF-8 -d out src/main/java/utp/eventos/domain/*.java src/main/java/utp/eventos/utils/*.java src/main/java/utp/eventos/App.java
java -cp out utp.eventos.App
```

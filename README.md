# Sistema de Gestión de Eventos Académicos

**Proyecto semestral de Programación I – Avance #2**  
Universidad Tecnológica de Panamá (UTP)

## Integrantes
- Christopher Alexis Coronel Cadiz
- Isaaid Caicedo Ebed Chavez
- Garriga Alexander

## Propósito
Sistema académico prototipo para administrar eventos, participantes, inscripciones, categorías, espacios, horarios, notificaciones y reportes de asistencia con programación orientada a objetos en Java.

## Estructura
- `src/main/java/utp/eventos/domain/`: 10 clases del diseño UML implementadas con atributos, constructores y métodos.
- `src/main/java/utp/eventos/App.java`: demostración y verificaciones funcionales.
- `src/main/java/utp/eventos/utils/IOUtils.java`: utilidad conservada del Avance #1 (existente en el repositorio).
- `docs/Tablas-semestral.docx`: tablas originales del Avance #1 conservadas en el repositorio.
- `docs/uml/diagrama_clases_eventos.png` y `.dot`: UML original conservado.
- `docs/uml/Diagrama_UML_actualizado.png` y `.dot`: UML actualizado para el Avance #2.
- `docs/avance2/Tablas_actualizadas.docx`: Tabla 1 y Tabla 2 del segundo avance.
- `docs/avance2/Resultados_pruebas_paquete.txt`: resultados de compilación y demostración.

## Clases y relaciones
Las clases son `Usuario`, `Participante`, `Organizador`, `Evento`, `Inscripcion`, `Horario`, `Espacio`, `Categoria`, `Reporte` y `Notificacion`. `Participante` y `Organizador` heredan de `Usuario`.

## Cómo compilar y ejecutar
Requiere JDK 17 o superior. Desde la carpeta principal del repositorio:

**Windows (PowerShell / CMD):**
```bat
mkdir out
javac -encoding UTF-8 -d out src/main/java/utp/eventos/domain/*.java src/main/java/utp/eventos/utils/*.java src/main/java/utp/eventos/App.java
java -cp out utp.eventos.App
```

**macOS/Linux:**
```bash
mkdir -p out
javac -encoding UTF-8 -d out src/main/java/utp/eventos/domain/*.java src/main/java/utp/eventos/utils/*.java src/main/java/utp/eventos/App.java
java -cp out utp.eventos.App
```

La ejecución del ejemplo crea `reporte_evento_1.txt` como archivo de salida; no debe incluirse en el repositorio.

## Alcance
Prototipo en memoria sin base de datos ni interfaz gráfica. Se implementan los escenarios principales y comprobaciones para presentación académica. Las correcciones técnicas se infieren del diseño; no se cuenta con las observaciones verbales del profesor por escrito.

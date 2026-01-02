# 📘 Ejercicio Taquillas Cine (PSP)

Repositorio para el ejercicio práctico de gestión de concurrencia "Taquillas de Cine" del módulo **Programación de Servicios y Procesos (PSP)** de 2º DAM.

El repositorio se encuentra en: [https://github.com/DanielTreto/EJTaquillasCine.git](https://github.com/DanielTreto/EJTaquillasCine.git)

## 📑 Índice de contenidos

### 🔹 UT2: Programación Multihilo (Concurrencia)

**Descripción:** Simulación de un sistema de venta de entradas en tiempo real utilizando programación concurrente en Java, gestionando recursos compartidos y evitando bloqueos.

**Versiones incluidas:**

* **Cine V1 (Versión Inicial)**
    * Arquitectura básica Cliente - Cola - Taquilla.
    * Uso de `Semaphore` para limitar el acceso a la cola.

* **Cine V2 (Versión Avanzada - 4 Colas)**
    * Simulación compleja con **4 Colas** y **2 Taquillas**.
    * Rechazo de clientes si las colas están llenas (Capacidad máx. 10).
    * Uso de `Collections.shuffle` para que las taquillas atiendan colas aleatoriamente.
    * Los clientes eligen una cola disponible al azar (no secuencial).
---

## 🚀 Cómo ejecutar los proyectos

1.  **Clona este repositorio:**
    ```bash
    git clone https://github.com/DanielTreto/EJTaquillasCine.git
    ```

2.  **Importa el proyecto** en tu IDE favorito (Eclipse, IntelliJ, VS Code).

3.  **Compila y ejecuta** según la versión que quieras probar:

    * **Para la V1:** Ejecuta la clase `cuatrovientos.dam.psp.java.EjTaquillasCine.V1.Cine4V`
    * **Para la V2:** Ejecuta la clase `cuatrovientos.dam.psp.java.EjTaquillasCine.V2.Cine4V`

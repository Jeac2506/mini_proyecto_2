# 🐉 DragonQuest – Mini Proyecto 1  
**Materia:** Programación Orientada a Eventos  
**Profesor:** Joshua Triana  
**Universidad del Valle**

---

## Integrantes  
- **Juan Esteban Aguirre Castañeda** – 202459676  
- **Kevin Julián López Moreno** – 202380379  

---

## Descripción del Proyecto  
**DragonQuest** es un **juego de combate por turnos** desarrollado en **Java**, en el que cuatro héroes se enfrentan a cuatro enemigos.  
Cada personaje cuenta con atributos como **HP**, **MP**, **ataque**, **defensa** y **velocidad**.  
Durante el combate, los héroes pueden **atacar, defenderse, usar habilidades o ítems**, mientras que los enemigos actúan mediante una **IA básica**.  

El juego se ejecuta en consola y busca aplicar los conceptos fundamentales de la **Programación Orientada a Eventos (POE)**, incluyendo:
- Herencia  
- Polimorfismo  
- Clases abstractas e interfaces  
- Encapsulamiento  
- Organización modular por paquetes

---

## Estructura del Proyecto

```plaintext
dragonquest/
│
├── main.java           → Clase principal (main)
│
├── combate/
│   └── Batalla.java               → Control del flujo de combate y turnos
│
├── estados/
│   └── EstadoAlterado.java        → Enum con estados (normal, paralizado, dormido, etc.)
│
├── habilidades/
│   ├── Habilidad.java             → Clase base abstracta para las habilidades
│   ├── Curacion.java              → Habilidad de curar HP
│   ├── CuracionGrupal.java              → Habilidad de curar HP al grupo
│   ├── Dormir.java              → Habilidad de Dormir al enemigo
│   ├── GolpeCritico.java              → Habilidad para que un golpe fisico tenga chance de ser un golpe critico
│   ├── Paralisis.java              → Habilidad de paralizar al enemigo
│   ├── RemoverEstado.java              → Habilidad para remover cualquier estado negativo 
│   ├── Veneno.java              → Habilidad de curar HP
│   ├── Aturdimiento.java              → Habilidad de envenenar al enemigo
│   └── DanioMagico.java           → Habilidad ofensiva mágica
│
├── items/
│   ├── Item.java                  → Clase base abstracta para ítems
│   ├── InventarioGrupo.java              → Clase auxiliar simple para pares
│   ├── PocionCuracion.java                → Restaura HP
│   ├── Antidoto.java              → Habilidad para eliminar el estado envenenado
│   └── PocionMagia.java                  → Restaura MP
│
└── personajes/
    ├── Personaje.java             → Clase base abstracta con atributos y métodos comunes
    ├── Heroe.java                 → Subclase que representa a los héroes
    └── Enemigo.java               → Subclase que representa a los enemigos
```
---

## Instrucciones para Colaboradores
**Si otro desarrollador desea contribuir:**

**Clonar el repositorio:**

- git clone https://github.com/Juliank100/Mini_Proyecto_1.git cd Mini_Proyecto_1


**Crear una nueva rama:**

- git checkout -b nombre-rama


**Realizar los cambios y hacer commit:**

- git add .
- git commit -m "Descripción del cambio"


**Subir los cambios y crear un Pull Request:**

- git push origin nombre-rama

- Luego abrir el PR en GitHub para revisión.

---

## Análisis del Diseño
- **¿Qué pasa si dos personajes tienen la misma velocidad?**

Si dos personajes tienen la misma velocidad, el sistema mantiene el orden de aparición dentro de la lista de turno.
Esto significa que el personaje agregado primero actuará antes que el segundo.

## ¿Qué sistema se usa para definir las habilidades (y que puedan tener efectos distintos)?

El código usa una clase abstracta **Habilidad** que obliga a cada habilidad a implementar el método **ejecutar().**
Gracias a eso, cada habilidad (por ejemplo **Curacion, DanioMagico,** etc.) puede tener comportamientos únicos, sin alterar la estructura base.

public abstract class Habilidad {
    protected String nombre;
    protected int costoMP;
    public abstract void ejecutar(Personaje usuario, List<Personaje> enemigos, List<Personaje> aliados, Scanner sc);
}

## ¿Cómo se podrían representar los distintos tipos de personajes o enemigos?

**Se logra mediante herencia y especialización:**

Heroe y Enemigo heredan de Personaje.

Cada subclase redefine su comportamiento (tomarTurno(), atacar(), etc.).

En el futuro podrían agregarse clases como:

class Mago extends Heroe { ... }
class Guerrero extends Heroe { ... }
class JefeFinal extends Enemigo { ... }

## ¿Cómo se garantiza que el código sea extensible (por ejemplo, para el Mini Proyecto 2)?

**El proyecto está diseñado siguiendo principios de extensibilidad:**

- Cada componente (combate, personajes, habilidades, ítems, estados) está en su propio paquete.

- Las clases abstractas (Personaje, Habilidad, Item) definen contratos que pueden ser ampliados fácilmente.

- Para futuras versiones se podrán agregar:

- Nuevas habilidades y efectos de estado.

- Tipos de personajes con comportamientos distintos.

- Un sistema de niveles o experiencia.

- Una interfaz gráfica (GUI) o base de datos sin alterar la lógica central.
---

## Posibles Extensiones Futuras

- Añadir más tipos de enemigos y habilidades.

- Guardar y cargar partidas.

- Crear una interfaz gráfica (GUI) para el combate.


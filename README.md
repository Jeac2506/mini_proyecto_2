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

El juego se ejecuta en consola y busca aplicar los conceptos fundamentales de la **Programación Orientada a Objetos (POO)**, incluyendo:
- Herencia  
- Polimorfismo  
- Clases abstractas e interfaces  
- Encapsulamiento  
- Organización modular por paquetes

---

## Estructura del Proyecto
dragonquest/
│
├── DragonQuestVIII.java           → Clase principal (main)
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
│   └── DanioMagico.java           → Habilidad ofensiva mágica
│
├── items/
│   ├── Item.java                  → Clase base abstracta para ítems
│   ├── Pocion.java                → Restaura HP
│   └── Eter.java                  → Restaura MP
│
└── personajes/
    ├── Personaje.java             → Clase base abstracta con atributos y métodos comunes
    ├── Heroe.java                 → Subclase que representa a los héroes
    └── Enemigo.java               → Subclase que representa a los enemigos


## Instrucciones para Colaboradores

**Si otro desarrollador desea contribuir:**

- Clonar el repositorio:

git clone https://github.com/Juliank100/Mini_Proyecto_1.git
cd Mini_Proyecto_1


- Crear una nueva rama:

git checkout -b nombre-rama


- Realizar los cambios y hacer commit:

git add .
git commit -m "Descripción del cambio"


- Subir los cambios y crear un Pull Request:

git push origin nombre-rama


- Luego abrir el PR en GitHub para revisión.

## Posibles Extensiones Futuras

- Añadir más tipos de enemigos y habilidades.

- Implementar estados alterados con efectos en turnos.

- Guardar y cargar partidas.

- Crear una interfaz gráfica (GUI) para el combate.

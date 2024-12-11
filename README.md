# Conversor de Monedas

Este es un proyecto de conversor de monedas en Java que permite convertir montos entre diferentes monedas utilizando tasas de cambio en tiempo real.

## Descripción

El **Conversor de Monedas** toma como entrada el código de la moneda de origen, la moneda de destino y el monto que deseas convertir. Utiliza la API de **ExchangeRate-API** para obtener las tasas de cambio actuales y realiza la conversión en tiempo real. El programa también muestra la tasa de cambio del día, el símbolo de cada moneda y el resultado de la conversión con los decimales correspondientes. el codigo y la data se encuentra en el branche master 

## Características

- Convierte entre varias monedas populares.
- Obtiene tasas de cambio en tiempo real a través de una API externa.
- Muestra la tasa de cambio con fecha.
- Muestra el resultado de la conversión con decimales y el símbolo de la moneda.

## Tecnologías Utilizadas

- **Java**: Lenguaje de programación principal.
- **API de ExchangeRate-API**: Para obtener las tasas de cambio en tiempo real.
- **DecimalFormat**: Para formatear la cantidad convertida a dos decimales.

## Requisitos

- Java 8 o superior.
- Conexión a Internet para acceder a la API de tasas de cambio.

## Instrucciones de Uso

1. Clona este repositorio:

   ```bash
   git clone https://github.com/ccHope92/conversor.git

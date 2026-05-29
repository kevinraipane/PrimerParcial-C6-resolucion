-- Estructura física mapeada exactamente desde tu CampanaEntity
CREATE TABLE IF NOT EXISTS campanas (id BIGINT AUTO_INCREMENT PRIMARY KEY, nombre_campana VARCHAR(255) NOT NULL, target_audiencia VARCHAR(255) NOT NULL, tipo_dispositivo VARCHAR(255) NOT NULL, fecha_fin TIMESTAMP NOT NULL, presupuesto_base DECIMAL(10,2) NOT NULL);

-- Limpieza preventiva para asegurar un entorno de ejecución controlado en memoria
TRUNCATE TABLE campanas;

-- CASO DE PRUEBA CLAVE 1: Campañas Activas con misma Audiencia y Dispositivo (Ideal para probar la regla de "Robo/Redistribución de Saldo")
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('CyberFest Gamers A', 'Gamers', 'Mobile', DATEADD(day, 10, NOW()), 150000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('CyberFest Gamers B', 'Gamers', 'Mobile', DATEADD(day, 12, NOW()), 80000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('CyberFest Gamers C', 'Gamers', 'Mobile', DATEADD(day, 15, NOW()), 350000.00);

-- CASO DE PRUEBA CLAVE 2: Campañas VENCIDAS (Debe fallar inmediatamente al intentar cargarles un Anuncio Nuevo)
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Liquidación de Verano Antigua', 'Moda', 'Desktop', DATEADD(day, -5, NOW()), 500000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Black Friday Pasado', 'Techies', 'Mobile', DATEADD(day, -1, NOW()), 950000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Promo Navideña Expirada', 'Familiar', 'SmartTV', DATEADD(month, -2, NOW()), 300000.00);

-- CASO DE PRUEBA CLAVE 3: Campañas Críticas / Próximas a vencer (< 48 horas, para probar la penalización de reembolso del 50%)
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Flash Sale Tech 24h', 'Techies', 'Mobile', DATEADD(hour, 18, NOW()), 120000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Cierre de Temporada Nocturno', 'Moda', 'Mobile', DATEADD(day, 1, NOW()), 75000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Últimas Horas Finanzas', 'Inversores', 'Desktop', DATEADD(hour, 40, NOW()), 250000.00);

-- MIX VARIADO Y SURTIDO: 91 registros adicionales para completar el pool de volumen y pruebas generales
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Lanzamiento Crypto Latam', 'Inversores', 'Mobile', DATEADD(day, 30, NOW()), 850000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Promo Running Zapatillas', 'Deportistas', 'Mobile', DATEADD(day, 20, NOW()), 430000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Curso Programación FullStack', 'Estudiantes', 'Desktop', DATEADD(day, 45, NOW()), 180000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Hamburgueserías Mar del Plata Tour', 'Gastronomía', 'Mobile', DATEADD(day, 7, NOW()), 90000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Suscripción Streaming Premium', 'Techies', 'SmartTV', DATEADD(day, 60, NOW()), 1200000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Viajes de Invierno Bariloche', 'Viajeros', 'Mobile', DATEADD(day, 15, NOW()), 650000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Indumentaria Otoño-Invierno', 'Moda', 'Mobile', DATEADD(day, 25, NOW()), 320000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Inscripciones UTN Nivelatorio', 'Estudiantes', 'Desktop', DATEADD(day, 40, NOW()), 50000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Herramientas de Construcción Profesional', 'Hogar', 'Desktop', DATEADD(day, 14, NOW()), 210000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Suplementos Gym Proteicos', 'Deportistas', 'Mobile', DATEADD(day, 11, NOW()), 140000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Hoteles All Inclusive Caribe', 'Viajeros', 'Desktop', DATEADD(day, 90, NOW()), 2500000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Inversiones Plazo Fijo Digital', 'Inversores', 'Mobile', DATEADD(day, 35, NOW()), 990000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Festival de Cine Internacional', 'Cultura', 'Tablet', DATEADD(day, 9, NOW()), 170000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Accesorios Gaming Mecánicos', 'Gamers', 'Desktop', DATEADD(day, 22, NOW()), 400000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Maratón Ciudad de Mar del Plata', 'Deportistas', 'Mobile', DATEADD(day, 5, NOW()), 300000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Colegios Inscripciones Abiertas', 'Familiar', 'Desktop', DATEADD(day, 50, NOW()), 150000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Laptops Corporativas Outlet', 'Techies', 'Desktop', DATEADD(day, 18, NOW()), 850000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Delivery de Sushi Premium', 'Gastronomía', 'Mobile', DATEADD(day, 6, NOW()), 110000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Seguros de Auto Cobertura Total', 'Hogar', 'Mobile', DATEADD(day, 45, NOW()), 720000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Consolas de Última Generación', 'Gamers', 'SmartTV', DATEADD(day, 30, NOW()), 1400000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Ropa de Bebé Algodón Orgánico', 'Familiar', 'Mobile', DATEADD(day, 16, NOW()), 130000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Software ERP para PYMEs', 'Profesionales', 'Desktop', DATEADD(day, 75, NOW()), 600000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Alquiler de Carros en Destino', 'Viajeros', 'Mobile', DATEADD(day, 24, NOW()), 380000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Cursos de Finanzas Personales', 'Inversores', 'Tablet', DATEADD(day, 19, NOW()), 220000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Cosmética Cruelty Free', 'Moda', 'Mobile', DATEADD(day, 28, NOW()), 290000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Electrodomésticos Eficiencia A', 'Hogar', 'Desktop', DATEADD(day, 33, NOW()), 950000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Indie Games Showcase', 'Gamers', 'Desktop', DATEADD(day, 8, NOW()), 70000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Vinos de Bodega de Autor', 'Gastronomía', 'Desktop', DATEADD(day, 21, NOW()), 410000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Becas de Posgrado Internacionales', 'Profesionales', 'Desktop', DATEADD(day, 60, NOW()), 500000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Equipamiento de Camping de Alta Montaña', 'Viajeros', 'Mobile', DATEADD(day, 13, NOW()), 240000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Muebles de Diseño Minimalista', 'Hogar', 'Desktop', DATEADD(day, 42, NOW()), 680000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Smartphones Gamas Media-Alta', 'Techies', 'Mobile', DATEADD(day, 17, NOW()), 1150000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Planes de Salud Joven', 'Profesionales', 'Mobile', DATEADD(day, 55, NOW()), 800000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Cervecerías Artesanales Marplatenses', 'Gastronomía', 'Mobile', DATEADD(day, 4, NOW()), 12000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Monitores 4K para Edición', 'Techies', 'Desktop', DATEADD(day, 26, NOW()), 730000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Juguetes Didácticos de Madera', 'Familiar', 'Tablet', DATEADD(day, 31, NOW()), 95000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Bicicletas Eléctricas Urbanas', 'Deportistas', 'Mobile', DATEADD(day, 48, NOW()), 620000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Plataforma Crowdfunding Inmobiliario', 'Inversores', 'Desktop', DATEADD(day, 50, NOW()), 1350000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Calzado de Seguridad Industrial', 'Profesionales', 'Desktop', DATEADD(day, 23, NOW()), 260000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Abonos de Teatro de Temporada', 'Cultura', 'Desktop', DATEADD(day, 12, NOW()), 190000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('E-books de Ciencia Ficción y Dune', 'Cultura', 'Tablet', DATEADD(day, 29, NOW()), 85000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Accesorios de Fotografía Avanzada', 'Techies', 'Desktop', DATEADD(day, 34, NOW()), 540000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Ropa Deportiva de Alta Compresión', 'Deportistas', 'Mobile', DATEADD(day, 16, NOW()), 310000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Cafeterías de Especialidad en MDP', 'Gastronomía', 'Mobile', DATEADD(day, 10, NOW()), 65000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Hardware para Minería de Datos', 'Techies', 'Desktop', DATEADD(day, 38, NOW()), 1600000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Decoración Básica de Interiores', 'Hogar', 'Mobile', DATEADD(day, 21, NOW()), 175000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Espectáculos Infantiles Vacaciones', 'Familiar', 'SmartTV', DATEADD(day, 14, NOW()), 220000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Asesoramiento Legal para Startups', 'Profesionales', 'Desktop', DATEADD(day, 44, NOW()), 450000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Aparatos de Domótica y Seguridad', 'Hogar', 'Mobile', DATEADD(day, 27, NOW()), 580000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Cruceros por la Patagonia', 'Viajeros', 'Desktop', DATEADD(day, 80, NOW()), 2100000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Tarjetas de Crédito Premium Black', 'Inversores', 'Mobile', DATEADD(day, 32, NOW()), 1250000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Marroquinería y Carteras de Cuero', 'Moda', 'Mobile', DATEADD(day, 18, NOW()), 270000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Insumos de Impresión 3D Filamentos', 'Techies', 'Desktop', DATEADD(day, 15, NOW()), 115000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Lencería y Ropa de Cama Diseño', 'Moda', 'Mobile', DATEADD(day, 22, NOW()), 195000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Kits de Herramientas Mecánicas', 'Hogar', 'Desktop', DATEADD(day, 19, NOW()), 230000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Periféricos Bluetooth de Oficina', 'Profesionales', 'Mobile', DATEADD(day, 36, NOW()), 340000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Indumentaria de Nieve Ski/Board', 'Viajeros', 'Mobile', DATEADD(day, 11, NOW()), 510000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Gimnasios Pases Libres Mensuales', 'Deportistas', 'Mobile', DATEADD(day, 25, NOW()), 200000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Restaurantes de Mariscos Puerto', 'Gastronomía', 'Desktop', DATEADD(day, 13, NOW()), 160000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Torneos de Esports Amateur', 'Gamers', 'Desktop', DATEADD(day, 6, NOW()), 45000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Clases de Yoga y Meditación Online', 'Familiar', 'Tablet', DATEADD(day, 29, NOW()), 75000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Maquinaria Agrícola Repuestos', 'Profesionales', 'Desktop', DATEADD(day, 50, NOW()), 890000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Maletas de Viaje Rígidas Ruedas', 'Viajeros', 'Mobile', DATEADD(day, 17, NOW()), 310000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Iluminación LED Inteligente WiFi', 'Hogar', 'Mobile', DATEADD(day, 23, NOW()), 145000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Suscripciones de Software de Diseño', 'Techies', 'Desktop', DATEADD(day, 41, NOW()), 670000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Calefactores Eléctricos de Bajo Consumo', 'Hogar', 'Desktop', DATEADD(day, 30, NOW()), 400000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Zapatillas de Skate Urbanas', 'Moda', 'Mobile', DATEADD(day, 24, NOW()), 260000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Planes de Ahorro Automotores', 'Familiar', 'Mobile', DATEADD(day, 60, NOW()), 1100000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Consultoría en Ciberseguridad Cloud', 'Profesionales', 'Desktop', DATEADD(day, 48, NOW()), 920000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Heladerías de Temporada Promo 2x1', 'Gastronomía', 'Mobile', DATEADD(day, 3, NOW()), 35000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Componentes para PC de Escritorio', 'Gamers', 'Desktop', DATEADD(day, 21, NOW()), 950000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Colecciones de Libros Infantiles', 'Familiar', 'Tablet', DATEADD(day, 16, NOW()), 68000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Protección Solar Línea Dermatológica', 'Moda', 'Mobile', DATEADD(day, 12, NOW()), 215000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Fondos Comunes de Inversión FCI', 'Inversores', 'Mobile', DATEADD(day, 37, NOW()), 1050000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Pinturas e Impermeabilizantes Techos', 'Hogar', 'Desktop', DATEADD(day, 14, NOW()), 390000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Consola de Sonido para Eventos', 'Cultura', 'Desktop', DATEADD(day, 26, NOW()), 480000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Planes Prepaga Tercera Edad', 'Familiar', 'Desktop', DATEADD(day, 52, NOW()), 880000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Equipamiento de Boxeo y MMA', 'Deportistas', 'Mobile', DATEADD(day, 19, NOW()), 155000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Catering para Eventos Empresariales', 'Gastronomía', 'Desktop', DATEADD(day, 45, NOW()), 740000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Tablets Gráficas para Ilustración', 'Techies', 'Tablet', DATEADD(day, 22, NOW()), 320000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Indumentaria Minimalista Urbana', 'Moda', 'Mobile', DATEADD(day, 27, NOW()), 230000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Accesorios para Mascotas Vitalcan', 'Familiar', 'Mobile', DATEADD(day, 18, NOW()), 90000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Herramientas Neumáticas Taller', 'Profesionales', 'Desktop', DATEADD(day, 33, NOW()), 430000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Bolsas de Dormir Térmicas Climas Fríos', 'Viajeros', 'Mobile', DATEADD(day, 15, NOW()), 125000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Seguros de Retiro y Capitalización', 'Inversores', 'Desktop', DATEADD(day, 40, NOW()), 610000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Parrillas Portátiles Balcón/Jardín', 'Hogar', 'Mobile', DATEADD(day, 10, NOW()), 190000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Teclados Mecánicos Custom RGB', 'Gamers', 'Desktop', DATEADD(day, 13, NOW()), 280000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Mochilas de Viaje Ergonómicas Mochileros', 'Viajeros', 'Mobile', DATEADD(day, 20, NOW()), 165000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Suscripciones Diarios Digitales Noticias', 'Profesionales', 'Mobile', DATEADD(day, 35, NOW()), 80000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('Cursos Prácticos SEO y Growth', 'Estudiantes', 'Desktop', DATEADD(day, 28, NOW()), 130000.00);
INSERT INTO campanas (nombre_campana, target_audiencia, tipo_dispositivo, fecha_fin, presupuesto_base) VALUES ('CyberFest Gamers Final Sweep', 'Gamers', 'Mobile', DATEADD(day, 45, NOW()), 600000.00);
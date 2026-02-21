-- Categorías
INSERT INTO categoria (nombre, descripcion) VALUES ('Electrónica', 'Dispositivos electrónicos y accesorios');
INSERT INTO categoria (nombre, descripcion) VALUES ('Hogar', 'Artículos para el hogar y decoración');
INSERT INTO categoria (nombre, descripcion) VALUES ('Oficina', 'Material de oficina y tecnología');

-- Proveedores
INSERT INTO proveedor (nombre, email, telefono) VALUES ('TechCorp SA', 'techcorp@email.com', '555-0001');
INSERT INTO proveedor (nombre, email, telefono) VALUES ('Global Supplies', 'global@email.com', '555-0002');
INSERT INTO proveedor (nombre, email, telefono) VALUES ('Office Solutions', 'office@email.com', '555-0003');
INSERT INTO proveedor (nombre, email, telefono) VALUES ('Home Goods Inc', 'homegoods@email.com', '555-0004');
INSERT INTO proveedor (nombre, email, telefono) VALUES ('Digital World', 'digital@email.com', '555-0005');
INSERT INTO proveedor (nombre, email, telefono) VALUES ('MegaStore', 'megastore@email.com', '555-0006');
INSERT INTO proveedor (nombre, email, telefono) VALUES ('TechDistributor', 'techdist@email.com', '555-0007');
INSERT INTO proveedor (nombre, email, telefono) VALUES ('Prime Suppliers', 'prime@email.com', '555-0008');

-- Productos de ejemplo
INSERT INTO producto (nombre, precio, stock, categoria_id, proveedor_id) VALUES ('Laptop Pro 15', 1299.99, 25, 1, 1);
INSERT INTO producto (nombre, precio, stock, categoria_id, proveedor_id) VALUES ('Mouse Inalámbrico', 29.99, 100, 1, 2);
INSERT INTO producto (nombre, precio, stock, categoria_id, proveedor_id) VALUES ('Teclado Mecánico', 89.99, 50, 1, 1);
INSERT INTO producto (nombre, precio, stock, categoria_id, proveedor_id) VALUES ('Monitor 27"', 349.99, 30, 1, 5);
INSERT INTO producto (nombre, precio, stock, categoria_id, proveedor_id) VALUES ('Lampara LED Escritorio', 45.99, 75, 2, 4);
INSERT INTO producto (nombre, precio, stock, categoria_id, proveedor_id) VALUES ('Juego Sábanas Queen', 79.99, 40, 2, 4);
INSERT INTO producto (nombre, precio, stock, categoria_id, proveedor_id) VALUES ('Papel A4 (500 hojas)', 12.99, 200, 3, 3);
INSERT INTO producto (nombre, precio, stock, categoria_id, proveedor_id) VALUES ('Bolígrafos (caja 50)', 8.99, 150, 3, 3);
INSERT INTO producto (nombre, precio, stock, categoria_id, proveedor_id) VALUES ('Webcam HD', 69.99, 60, 1, 7);
INSERT INTO producto (nombre, precio, stock, categoria_id, proveedor_id) VALUES ('Auriculares USB', 49.99, 80, 1, 2);

-- Tareas de ejemplo
INSERT INTO tarea (titulo, descripcion, terminada) VALUES ('Revisar inventario', 'Verificar stock de productos electrónicos', false);
INSERT INTO tarea (titulo, descripcion, terminada) VALUES ('Actualizar precios', 'Revisar y actualizar precios de proveedores', false);
INSERT INTO tarea (titulo, descripcion, terminada) VALUES ('Pedido mensual', 'Realizar pedido a TechCorp SA', true);
INSERT INTO tarea (titulo, descripcion, terminada) VALUES ('Organizar almacén', 'Reorganizar productos en almacén', false);
INSERT INTO tarea (titulo, descripcion, terminada) VALUES ('Facturación', 'Emitir facturas del mes', true);

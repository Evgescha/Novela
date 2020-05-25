INSERT INTO Genre(id,name) VALUES
(1,'Action'),
(2,'Comedi'),
(3,'Dramma'),
(4,'Triller'),
(5,'School'),
(6,'Fantasu')
 ON CONFLICT (id) DO NOTHING;

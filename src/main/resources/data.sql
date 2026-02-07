INSERT INTO ploentuin_user (username, email, password, email_verified, role, email_verification_token, reset_token)
VALUES
    ('alice', 'alice@example.com', '$2a$10$7QIX9Lx9GjNx5F5/Z6RkiuF6l5O2ruqM/9txfN02X8xC6.TvPDp2K', true, 'USER', NULL, NULL),
    ('bob', 'bob@example.com', '$2a$10$7QIX9Lx9GjNx5F5/Z6RkiuF6l5O2ruqM/9txfN02X8xC6.TvPDp2K', true, 'USER', NULL, NULL),
    ('charlie', 'charlie@example.com', '$2a$10$7QIX9Lx9GjNx5F5/Z6RkiuF6l5O2ruqM/9txfN02X8xC6.TvPDp2K', true, 'USER', NULL, NULL),
    ('diana', 'diana@example.com', '$2a$10$7QIX9Lx9GjNx5F5/Z6RkiuF6l5O2ruqM/9txfN02X8xC6.TvPDp2K', true, 'USER', NULL, NULL),
    ('edward', 'edward@example.com', '$2a$10$7QIX9Lx9GjNx5F5/Z6RkiuF6l5O2ruqM/9txfN02X8xC6.TvPDp2K', true, 'USER', NULL, NULL);


INSERT INTO planner_item_catalog (name, colour, image_url, type)
VALUES
-- BUSHES
('Boxwood', '#228B22', 'https://getpotted.com/upload/resize_cache/iblock/3fa/256_256_1/xlwx52un3qi500wgauf3k976mka4dawwg.jpg.pagespeed.ic.Qswcf18gus.jpg', 'BUSHES'),
('Hydrangea Bush', '#FF69B4', 'https://www.theplantcompany.co.nz/file/getimage/shop/jpg/sm/hydrangea-alpen-gluten.webp', 'BUSHES'),
('Lilac Bush', '#9370DB', 'https://www.starkbros.com/images/dynamic/4182-256x256-fill.jpg', 'BUSHES'),

-- FLOWERS
('Rose', '#FF0000', 'https://lens-roses.com/web/image/product.template/17720/image_256/La Rose de Molinard®?unique=320b647', 'FLOWERS'),
('Tulip', '#FFA500', 'https://files.idyllic.app/files/static/278360?width=256&optimizer=image', 'FLOWERS'),
('Daisy', '#FFFF00', 'https://www.irishwildflowers.ie/images/folder1/53a1.jpg', 'FLOWERS'),

-- TREES
('Oak', '#8B4513', 'https://www.alanshelley.org/wp-content/uploads/2019/08/TheEnglishOak.jpg', 'TREES'),
('Pine', '#006400', 'https://media.tractorsupply.com/is/image/TractorSupplyCompany/1913349?$256$', 'TREES'),
('Maple', '#A52A2A', 'https://i.pinimg.com/564x/d3/a6/fd/d3a6fd5b732ae4bfd11c7cd7167b57cc.jpg', 'TREES'),

-- FRUIT_TREES
('Apple Tree', '#FF4500', 'https://images.stockcake.com/public/2/7/3/2734827a-bfbe-47eb-a130-6ff82b4ebdac_medium/bountiful-apple-tree-stockcake.jpg', 'FRUIT_TREES'),
('Cherry Tree', '#FF1493', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXlqagECV25ELPeADFK72R_cAA6F8Tlo6zCQ&s', 'FRUIT_TREES'),
('Pear Tree', '#9ACD32', 'https://www.starkbros.com/images/dynamic/7461-256x256-fill.jpg', 'FRUIT_TREES'),

-- HERBS
('Basil', '#32CD32', 'https://www.famflowerfarm.nl/web/image/product.template/1522/image_256', 'HERBS'),
('Thyme', '#556B2F', 'https://terrepromise.ca/web/image/product.template/1076/image_256/%5B345%5D%20Thyme%20%28Thymus%20vulgaris%29?unique=b7f0e73', 'HERBS'),
('Mint', '#00FA9A', 'https://images.stockcake.com/public/b/e/0/be0cb661-1082-459d-b8c4-44439a0a49b6_medium/fresh-mint-leaves-stockcake.jpg', 'HERBS'),

-- CLIMBERS
('Clematis', '#9400D3', 'https://www.naturalbulbs.nl/web/image/product.template/14630/image_256', 'CLIMBERS'),
('Wisteria', '#BA55D3', 'https://www.theplantcompany.co.nz/file/getimage/shop/jpg/sm/wisteria_sinensis_caroline4.webp', 'CLIMBERS'),
('Ivy', '#006400', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS-aDvhlczIAGrPnG3IzHluoUDQSp_Dvuw8SA&s', 'CLIMBERS'),

-- GRASSES
('Ornamental Grass', '#7CFC00', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTwfrWdNo1lc8reFQsxS19dbRJBDP0gd7iYWw&s', 'GRASSES'),
('Fescue', '#228B22', 'https://www.picturethisai.com/image-handle/website_cmsname/image/1080/153937503445843973.jpeg?x-oss-process=image/format,webp/resize,s_256&v=1.0', 'GRASSES'),
('Bamboo', '#32CD32', 'https://cdn.shopify.com/s/files/1/0955/5545/3192/files/Fargesia_robusta_campbell_close_up.jpg?v=1755944565&width=256', 'GRASSES'),

-- FRUITS
('Strawberry', '#FF6347', 'https://images.meesho.com/images/products/651454817/lieys_512.webp?width=256', 'FRUITS'),
('Blueberry', '#4169E1', 'https://www.starkbros.com/images/dynamic/4322-256x256-fill.jpg', 'FRUITS'),
('Raspberry', '#DC143C', 'https://www.starkbros.com/images/dynamic/6138-256x256-fill.jpg', 'FRUITS'),

-- VEGETABLES
('Tomato', '#FF4500', 'https://www.famflowerfarm.nl/web/image/product.template/1692/image_256', 'VEGETABLES'),
('Carrot', '#FFA500', 'https://www.famflowerfarm.nl/web/image/product.template/1689/image_256', 'VEGETABLES'),
('Iceberglettuce', '#7CFC00', 'https://dewijamesbutcherscouk-7700-static.symphonycommerce.io/images/2020/05/contain/256x256/555e245314955733241d513c77c5b570.jpg', 'VEGETABLES'),

-- AQUATICS
('Water Lily', '#FF69B4', 'https://images.stockcake.com/public/a/0/0/a00d6db2-e4de-4f68-acaa-d8db072afd9b_medium/serene-water-lily-stockcake.jpg', 'AQUATICS'),
('Lotus', '#FFD700', 'https://images.stockcake.com/public/9/6/5/96576ab6-70c4-427e-9f21-b119668f1b48_medium/serene-lotus-bloom-stockcake.jpg', 'AQUATICS'),
('Cattail', '#8B4513', 'https://www.picturethisai.com/image-handle/website_cmsname/image/1080/154046020089544724.jpeg?x-oss-process=image/format,webp/resize,s_256&v=1.0', 'AQUATICS'),

-- SUCCULENTS
('Aloe Vera', '#98FB98', 'https://plantsvszombies.wiki.gg/images/AloeVera.jpg?334afd', 'SUCCULENTS'),
('Echeveria', '#7FFFD4', 'https://static.wixstatic.com/media/2c3d2c_4e539e471ce642d3a62d0f7d6819f5a0~mv2.jpg/v1/fill/w_256,h_256,al_c,q_80,usm_0.66_1.00_0.01,enc_avif,quality_auto/2c3d2c_4e539e471ce642d3a62d0f7d6819f5a0~mv2.jpg', 'SUCCULENTS'),
('Sedum', '#32CD32', 'https://static.wixstatic.com/media/2c3d2c_bc1f6a76abf34545961a2bc422f584fb~mv2.jpg/v1/fill/w_256,h_256,al_c,q_80,usm_0.66_1.00_0.01,enc_avif,quality_auto/2c3d2c_bc1f6a76abf34545961a2bc422f584fb~mv2.jpg', 'SUCCULENTS'),

-- GROUNDS
('Sand', '#7CFC00', 'https://excluton.nl/img/products/thumbnails/6000112.jpg', 'GROUNDS'),
('Mulch', '#8B4513', 'https://www.mrmulch.com/web/image/product.template/5567/image_256', 'GROUNDS'),
('Gravel', '#D3D3D3', 'https://www.arroway-textures.ch/wp-content/uploads/2019/07/thumb_gravel-072_2-256x256.jpg', 'GROUNDS');

-- PLANNERS
INSERT INTO planner (user_id, title, row_count, column_count, created_at, updated_at)
VALUES
    ((SELECT id FROM ploentuin_user WHERE username = 'alice'), 'Alice''s Front Garden', 6, 8, now(), now()),
    ((SELECT id FROM ploentuin_user WHERE username = 'bob'), 'Bob''s Backyard', 8, 10, now(), now()),
    ((SELECT id FROM ploentuin_user WHERE username = 'charlie'), 'Charlie''s Veggie Patch', 5, 6, now(), now());

-- PLACEMENTS
INSERT INTO planner_item_placement (planner_id, catalog_item_id, row_num, col_num)
VALUES
    ((SELECT id FROM planner WHERE title = 'Alice''s Front Garden'), (SELECT id FROM planner_item_catalog WHERE name = 'Boxwood'), 0, 0),
    ((SELECT id FROM planner WHERE title = 'Alice''s Front Garden'), (SELECT id FROM planner_item_catalog WHERE name = 'Boxwood'), 0, 1),
    ((SELECT id FROM planner WHERE title = 'Alice''s Front Garden'), (SELECT id FROM planner_item_catalog WHERE name = 'Rose'), 2, 3),
    ((SELECT id FROM planner WHERE title = 'Alice''s Front Garden'), (SELECT id FROM planner_item_catalog WHERE name = 'Tulip'), 3, 3),
    ((SELECT id FROM planner WHERE title = 'Alice''s Front Garden'), (SELECT id FROM planner_item_catalog WHERE name = 'Daisy'), 3, 4),

    ((SELECT id FROM planner WHERE title = 'Bob''s Backyard'), (SELECT id FROM planner_item_catalog WHERE name = 'Oak'), 1, 1),
    ((SELECT id FROM planner WHERE title = 'Bob''s Backyard'), (SELECT id FROM planner_item_catalog WHERE name = 'Pine'), 1, 6),
    ((SELECT id FROM planner WHERE title = 'Bob''s Backyard'), (SELECT id FROM planner_item_catalog WHERE name = 'Ornamental Grass'), 4, 2),
    ((SELECT id FROM planner WHERE title = 'Bob''s Backyard'), (SELECT id FROM planner_item_catalog WHERE name = 'Fescue'), 4, 3),

    ((SELECT id FROM planner WHERE title = 'Charlie''s Veggie Patch'), (SELECT id FROM planner_item_catalog WHERE name = 'Tomato'), 1, 1),
    ((SELECT id FROM planner WHERE title = 'Charlie''s Veggie Patch'), (SELECT id FROM planner_item_catalog WHERE name = 'Carrot'), 1, 2),
    ((SELECT id FROM planner WHERE title = 'Charlie''s Veggie Patch'), (SELECT id FROM planner_item_catalog WHERE name = 'Iceberglettuce'), 2, 1),
    ((SELECT id FROM planner WHERE title = 'Charlie''s Veggie Patch'), (SELECT id FROM planner_item_catalog WHERE name = 'Basil'), 3, 1),
    ((SELECT id FROM planner WHERE title = 'Charlie''s Veggie Patch'), (SELECT id FROM planner_item_catalog WHERE name = 'Mint'), 3, 2);

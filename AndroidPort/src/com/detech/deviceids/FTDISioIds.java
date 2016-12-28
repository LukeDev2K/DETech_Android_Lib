package com.detech.deviceids;

public class FTDISioIds
{
    private FTDISioIds()
    {

    }

    /* Different products and vendors using FTDI chipsets
     * From current FTDI SIO linux driver:
     * https://github.com/torvalds/linux/blob/164c09978cebebd8b5fc198e9243777dbaecdfa0/drivers/usb/serial/ftdi_sio_ids.h
     * */
    private static final ConcreteDevice[] ftdiDevices = new ConcreteDevice[]
            {
                    new ConcreteDevice(0x0403, 0x6001),
                    new ConcreteDevice(0x0403, 0x6006),
                    new ConcreteDevice(0x0403, 0x6010),
                    new ConcreteDevice(0x0403, 0x6011),
                    new ConcreteDevice(0x0403, 0x6014),
                    new ConcreteDevice(0x0403, 0x6015),
                    new ConcreteDevice(0x0403, 0x8372),
                    new ConcreteDevice(0x0403, 0xfbfa),
                    new ConcreteDevice(0x0403, 0x6002),
                    new ConcreteDevice(0x0403, 0x9e90),
                    new ConcreteDevice(0x0403, 0x9f80),
                    new ConcreteDevice(0x0403, 0xa6d0),
                    new ConcreteDevice(0x0403, 0xabb8),
                    new ConcreteDevice(0x0403, 0xabb9),
                    new ConcreteDevice(0x0403, 0xb810),
                    new ConcreteDevice(0x0403, 0xb811),
                    new ConcreteDevice(0x0403, 0xb812),
                    new ConcreteDevice(0x0403, 0xbaf8),
                    new ConcreteDevice(0x0403, 0xbcd8),
                    new ConcreteDevice(0x0403, 0xbcd9),
                    new ConcreteDevice(0x0403, 0xbcda),
                    new ConcreteDevice(0x0403, 0xbdc8),
                    new ConcreteDevice(0x0403, 0xbfd8),
                    new ConcreteDevice(0x0403, 0xbfd9),
                    new ConcreteDevice(0x0403, 0xbfda),
                    new ConcreteDevice(0x0403, 0xbfdb),
                    new ConcreteDevice(0x0403, 0xbfdc),
                    new ConcreteDevice(0x0403, 0xbfdd),
                    new ConcreteDevice(0x0403, 0xc1e0),
                    new ConcreteDevice(0x0403, 0xc7d0),
                    new ConcreteDevice(0x0403, 0xc850),
                    new ConcreteDevice(0x0403, 0xc991),
                    new ConcreteDevice(0x0403, 0xcaa0),
                    new ConcreteDevice(0x0403, 0xcc48),
                    new ConcreteDevice(0x0403, 0xcc49),
                    new ConcreteDevice(0x0403, 0xcc4a),
                    new ConcreteDevice(0x0403, 0xcff8),
                    new ConcreteDevice(0x0403, 0xd010),
                    new ConcreteDevice(0x0403, 0xd011),
                    new ConcreteDevice(0x0403, 0xd012),
                    new ConcreteDevice(0x0403, 0xd013),
                    new ConcreteDevice(0x0403, 0xd014),
                    new ConcreteDevice(0x0403, 0xd015),
                    new ConcreteDevice(0x0403, 0xd016),
                    new ConcreteDevice(0x0403, 0xd017),
                    new ConcreteDevice(0x0403, 0xd070),
                    new ConcreteDevice(0x0403, 0xd071),
                    new ConcreteDevice(0x0403, 0xd678),
                    new ConcreteDevice(0x0403, 0xd738),
                    new ConcreteDevice(0x0403, 0xd739),
                    new ConcreteDevice(0x0403, 0xd780),
                    new ConcreteDevice(0x0403, 0xf070),
                    new ConcreteDevice(0x0403, 0xd388),
                    new ConcreteDevice(0x0403, 0xd389),
                    new ConcreteDevice(0x0403, 0xd38a),
                    new ConcreteDevice(0x0403, 0xd38b),
                    new ConcreteDevice(0x0403, 0xd38c),
                    new ConcreteDevice(0x0403, 0xd38d),
                    new ConcreteDevice(0x0403, 0xd38e),
                    new ConcreteDevice(0x0403, 0xd38f),
                    new ConcreteDevice(0x0403, 0xd491),
                    new ConcreteDevice(0x0403, 0xda70),
                    new ConcreteDevice(0x0403, 0xda71),
                    new ConcreteDevice(0x0403, 0xda72),
                    new ConcreteDevice(0x0403, 0xda73),
                    new ConcreteDevice(0x0403, 0xda74),
                    new ConcreteDevice(0x0403, 0xdaf8),
                    new ConcreteDevice(0x0403, 0xdaf9),
                    new ConcreteDevice(0x0403, 0xdafa),
                    new ConcreteDevice(0x0403, 0xdafb),
                    new ConcreteDevice(0x0403, 0xdafc),
                    new ConcreteDevice(0x0403, 0xdafd),
                    new ConcreteDevice(0x0403, 0xdafe),
                    new ConcreteDevice(0x0403, 0xdaff),
                    new ConcreteDevice(0x0403, 0xdc00),
                    new ConcreteDevice(0x0403, 0xdc01),
                    new ConcreteDevice(0x0403, 0xdd20),
                    new ConcreteDevice(0x0403, 0xdf28),
                    new ConcreteDevice(0x0403, 0xdf30),
                    new ConcreteDevice(0x0403, 0xdf32),
                    new ConcreteDevice(0x0403, 0xdf31),
                    new ConcreteDevice(0x0403, 0xdf33),
                    new ConcreteDevice(0x0403, 0xdf35),
                    new ConcreteDevice(0x0403, 0xe050),
                    new ConcreteDevice(0x0403, 0xc006),
                    new ConcreteDevice(0x0403, 0xe000),
                    new ConcreteDevice(0x0403, 0xe001),
                    new ConcreteDevice(0x0403, 0xe002),
                    new ConcreteDevice(0x0403, 0xe004),
                    new ConcreteDevice(0x0403, 0xe006),
                    new ConcreteDevice(0x0403, 0xe008),
                    new ConcreteDevice(0x0403, 0xe009),
                    new ConcreteDevice(0x0403, 0xe00a),
                    new ConcreteDevice(0x0403, 0xe0e8),
                    new ConcreteDevice(0x0403, 0xe0e9),
                    new ConcreteDevice(0x0403, 0xe0ea),
                    new ConcreteDevice(0x0403, 0xe0eb),
                    new ConcreteDevice(0x0403, 0xe0ec),
                    new ConcreteDevice(0x0403, 0xe0ee),
                    new ConcreteDevice(0x0403, 0xe0ef),
                    new ConcreteDevice(0x0403, 0xe0f0),
                    new ConcreteDevice(0x0403, 0xe0f1),
                    new ConcreteDevice(0x0403, 0xe0f2),
                    new ConcreteDevice(0x0403, 0xe0f3),
                    new ConcreteDevice(0x0403, 0xe0f4),
                    new ConcreteDevice(0x0403, 0xe0f5),
                    new ConcreteDevice(0x0403, 0xe0f6),
                    new ConcreteDevice(0x0403, 0xe0f7),
                    new ConcreteDevice(0x0403, 0xe40b),
                    new ConcreteDevice(0x0403, 0xf068),
                    new ConcreteDevice(0x0403, 0xf069),
                    new ConcreteDevice(0x0403, 0xf06a),
                    new ConcreteDevice(0x0403, 0xf06b),
                    new ConcreteDevice(0x0403, 0xf06c),
                    new ConcreteDevice(0x0403, 0xf06d),
                    new ConcreteDevice(0x0403, 0xf06e),
                    new ConcreteDevice(0x0403, 0xf06f),
                    new ConcreteDevice(0x0403, 0xfb58),
                    new ConcreteDevice(0x0403, 0xfb5a),
                    new ConcreteDevice(0x0403, 0xfb5b),
                    new ConcreteDevice(0x0403, 0xfb59),
                    new ConcreteDevice(0x0403, 0xfb5c),
                    new ConcreteDevice(0x0403, 0xfb5d),
                    new ConcreteDevice(0x0403, 0xfb5e),
                    new ConcreteDevice(0x0403, 0xfb5f),
                    new ConcreteDevice(0x0403, 0xe520),
                    new ConcreteDevice(0x0403, 0xe548),
                    new ConcreteDevice(0x0403, 0xe6c8),
                    new ConcreteDevice(0x0403, 0xe700),
                    new ConcreteDevice(0x0403, 0xe808),
                    new ConcreteDevice(0x0403, 0xe809),
                    new ConcreteDevice(0x0403, 0xe80a),
                    new ConcreteDevice(0x0403, 0xe80b),
                    new ConcreteDevice(0x0403, 0xe80c),
                    new ConcreteDevice(0x0403, 0xe80d),
                    new ConcreteDevice(0x0403, 0xe80e),
                    new ConcreteDevice(0x0403, 0xe80f),
                    new ConcreteDevice(0x0403, 0xe888),
                    new ConcreteDevice(0x0403, 0xe889),
                    new ConcreteDevice(0x0403, 0xe88a),
                    new ConcreteDevice(0x0403, 0xe88b),
                    new ConcreteDevice(0x0403, 0xe88c),
                    new ConcreteDevice(0x0403, 0xe88d),
                    new ConcreteDevice(0x0403, 0xe88e),
                    new ConcreteDevice(0x0403, 0xe88f),
                    new ConcreteDevice(0x0403, 0xea90),
                    new ConcreteDevice(0x0403, 0xebe0),
                    new ConcreteDevice(0x0403, 0xec88),
                    new ConcreteDevice(0x0403, 0xec89),
                    new ConcreteDevice(0x0403, 0xed22),
                    new ConcreteDevice(0x0403, 0xed74),
                    new ConcreteDevice(0x0403, 0xed73),
                    new ConcreteDevice(0x0403, 0xed72),
                    new ConcreteDevice(0x0403, 0xed71),
                    new ConcreteDevice(0x0403, 0xee18),
                    new ConcreteDevice(0x0403, 0xeee8),
                    new ConcreteDevice(0x0403, 0xeee9),
                    new ConcreteDevice(0x0403, 0xeeea),
                    new ConcreteDevice(0x0403, 0xeeeb),
                    new ConcreteDevice(0x0403, 0xeeec),
                    new ConcreteDevice(0x0403, 0xeeed),
                    new ConcreteDevice(0x0403, 0xeeee),
                    new ConcreteDevice(0x0403, 0xeeef),
                    new ConcreteDevice(0x0403, 0xef50),
                    new ConcreteDevice(0x0403, 0xef51),
                    new ConcreteDevice(0x0403, 0xf0c0),
                    new ConcreteDevice(0x0403, 0xf0c8),
                    new ConcreteDevice(0x0403, 0xf0e9),
                    new ConcreteDevice(0x0403, 0xf0ee),
                    new ConcreteDevice(0x0403, 0xf208),
                    new ConcreteDevice(0x0403, 0xf2d0),
                    new ConcreteDevice(0x0403, 0xf3c0),
                    new ConcreteDevice(0x0403, 0xf3c1),
                    new ConcreteDevice(0x0403, 0xf3c2),
                    new ConcreteDevice(0x0403, 0xf448),
                    new ConcreteDevice(0x0403, 0xf449),
                    new ConcreteDevice(0x0403, 0xf44a),
                    new ConcreteDevice(0x0403, 0xf44b),
                    new ConcreteDevice(0x0403, 0xf44c),
                    new ConcreteDevice(0x0403, 0xf460),
                    new ConcreteDevice(0x0403, 0xf680),
                    new ConcreteDevice(0x0403, 0xf850),
                    new ConcreteDevice(0x0403, 0xf9d0),
                    new ConcreteDevice(0x0403, 0xf9d1),
                    new ConcreteDevice(0x0403, 0xf9d2),
                    new ConcreteDevice(0x0403, 0xf9d3),
                    new ConcreteDevice(0x0403, 0xf9d4),
                    new ConcreteDevice(0x0403, 0xf9d5),
                    new ConcreteDevice(0x0403, 0xfa00),
                    new ConcreteDevice(0x0403, 0xfa01),
                    new ConcreteDevice(0x0403, 0xfa02),
                    new ConcreteDevice(0x0403, 0xfa03),
                    new ConcreteDevice(0x0403, 0xfa04),
                    new ConcreteDevice(0x0403, 0xfa05),
                    new ConcreteDevice(0x0403, 0xfa06),
                    new ConcreteDevice(0x0403, 0xfa78),
                    new ConcreteDevice(0x0403, 0xfad0),
                    new ConcreteDevice(0x0403, 0xfaf0),
                    new ConcreteDevice(0x0403, 0xfc70),
                    new ConcreteDevice(0x0403, 0xfc71),
                    new ConcreteDevice(0x0403, 0xfc72),
                    new ConcreteDevice(0x0403, 0xfc73),
                    new ConcreteDevice(0x0403, 0xfc82),
                    new ConcreteDevice(0x0403, 0xfc8a),
                    new ConcreteDevice(0x0403, 0xfc8b),
                    new ConcreteDevice(0x0403, 0xfc60),
                    new ConcreteDevice(0x0403, 0xfd60),
                    new ConcreteDevice(0x0403, 0xff20),
                    new ConcreteDevice(0x0403, 0xf857),
                    new ConcreteDevice(0x0403, 0xfa10),
                    new ConcreteDevice(0x0403, 0xfa88),
                    new ConcreteDevice(0x0403, 0xfb99),
                    new ConcreteDevice(0x0403, 0xfc08),
                    new ConcreteDevice(0x0403, 0xfc09),
                    new ConcreteDevice(0x0403, 0xfc0a),
                    new ConcreteDevice(0x0403, 0xfc0b),
                    new ConcreteDevice(0x0403, 0xfc0c),
                    new ConcreteDevice(0x0403, 0xfc0d),
                    new ConcreteDevice(0x0403, 0xfc0e),
                    new ConcreteDevice(0x0403, 0xfc0f),
                    new ConcreteDevice(0x0403, 0xfe38),
                    new ConcreteDevice(0x0403, 0xff00),
                    new ConcreteDevice(0x0403, 0xff38),
                    new ConcreteDevice(0x0403, 0xff39),
                    new ConcreteDevice(0x0403, 0xff3a),
                    new ConcreteDevice(0x0403, 0xff3b),
                    new ConcreteDevice(0x0403, 0xff3c),
                    new ConcreteDevice(0x0403, 0xff3d),
                    new ConcreteDevice(0x0403, 0xff3e),
                    new ConcreteDevice(0x0403, 0xff3f),
                    new ConcreteDevice(0x0403, 0xffa8),
                    new ConcreteDevice(0x0403, 0xfa33),
                    new ConcreteDevice(0x0403, 0x8a98),
                    new ConcreteDevice(0x03eb, 0x2109),
                    new ConcreteDevice(0x0456, 0xf000),
                    new ConcreteDevice(0x0456, 0xf001),
                    new ConcreteDevice(0x04d8, 0x000a),
                    new ConcreteDevice(0x0584, 0xb020),
                    new ConcreteDevice(0x0647, 0x0100),
                    new ConcreteDevice(0x06CE, 0x8311),
                    new ConcreteDevice(0x06D3, 0x0284),
                    new ConcreteDevice(0x0856, 0xac01),
                    new ConcreteDevice(0x0856, 0xac02),
                    new ConcreteDevice(0x0856, 0xac03),
                    new ConcreteDevice(0x0856, 0xac11),
                    new ConcreteDevice(0x0856, 0xac12),
                    new ConcreteDevice(0x0856, 0xac16),
                    new ConcreteDevice(0x0856, 0xac17),
                    new ConcreteDevice(0x0856, 0xac18),
                    new ConcreteDevice(0x0856, 0xac19),
                    new ConcreteDevice(0x0856, 0xac25),
                    new ConcreteDevice(0x0856, 0xac26),
                    new ConcreteDevice(0x0856, 0xac27),
                    new ConcreteDevice(0x0856, 0xac33),
                    new ConcreteDevice(0x0856, 0xac34),
                    new ConcreteDevice(0x0856, 0xac49),
                    new ConcreteDevice(0x0856, 0xac50),
                    new ConcreteDevice(0x0856, 0xba02),
                    new ConcreteDevice(0x093c, 0x0601),
                    new ConcreteDevice(0x093c, 0x0701),
                    new ConcreteDevice(0x0acd, 0x0300),
                    new ConcreteDevice(0x0b39, 0x0103),
                    new ConcreteDevice(0x0b39, 0x0421),
                    new ConcreteDevice(0x0c26, 0x0004),
                    new ConcreteDevice(0x0c26, 0x0018),
                    new ConcreteDevice(0x0c26, 0x0009),
                    new ConcreteDevice(0x0c26, 0x000a),
                    new ConcreteDevice(0x0c26, 0x000b),
                    new ConcreteDevice(0x0c26, 0x000c),
                    new ConcreteDevice(0x0c26, 0x000d),
                    new ConcreteDevice(0x0c26, 0x0010),
                    new ConcreteDevice(0x0c26, 0x0011),
                    new ConcreteDevice(0x0c26, 0x0012),
                    new ConcreteDevice(0x0c26, 0x0013),
                    new ConcreteDevice(0x0c33, 0x0010),
                    new ConcreteDevice(0x0c52, 0x2101),
                    new ConcreteDevice(0x0c52, 0x2101),
                    new ConcreteDevice(0x0c52, 0x2102),
                    new ConcreteDevice(0x0c52, 0x2103),
                    new ConcreteDevice(0x0c52, 0x2104),
                    new ConcreteDevice(0x0c52, 0x9020),
                    new ConcreteDevice(0x0c52, 0x2211),
                    new ConcreteDevice(0x0c52, 0x2221),
                    new ConcreteDevice(0x0c52, 0x2212),
                    new ConcreteDevice(0x0c52, 0x2222),
                    new ConcreteDevice(0x0c52, 0x2213),
                    new ConcreteDevice(0x0c52, 0x2223),
                    new ConcreteDevice(0x0c52, 0x2411),
                    new ConcreteDevice(0x0c52, 0x2421),
                    new ConcreteDevice(0x0c52, 0x2431),
                    new ConcreteDevice(0x0c52, 0x2441),
                    new ConcreteDevice(0x0c52, 0x2412),
                    new ConcreteDevice(0x0c52, 0x2422),
                    new ConcreteDevice(0x0c52, 0x2432),
                    new ConcreteDevice(0x0c52, 0x2442),
                    new ConcreteDevice(0x0c52, 0x2413),
                    new ConcreteDevice(0x0c52, 0x2423),
                    new ConcreteDevice(0x0c52, 0x2433),
                    new ConcreteDevice(0x0c52, 0x2443),
                    new ConcreteDevice(0x0c52, 0x2811),
                    new ConcreteDevice(0x0c52, 0x2821),
                    new ConcreteDevice(0x0c52, 0x2831),
                    new ConcreteDevice(0x0c52, 0x2841),
                    new ConcreteDevice(0x0c52, 0x2851),
                    new ConcreteDevice(0x0c52, 0x2861),
                    new ConcreteDevice(0x0c52, 0x2871),
                    new ConcreteDevice(0x0c52, 0x2881),
                    new ConcreteDevice(0x0c52, 0x2812),
                    new ConcreteDevice(0x0c52, 0x2822),
                    new ConcreteDevice(0x0c52, 0x2832),
                    new ConcreteDevice(0x0c52, 0x2842),
                    new ConcreteDevice(0x0c52, 0x2852),
                    new ConcreteDevice(0x0c52, 0x2862),
                    new ConcreteDevice(0x0c52, 0x2872),
                    new ConcreteDevice(0x0c52, 0x2882),
                    new ConcreteDevice(0x0c52, 0x2813),
                    new ConcreteDevice(0x0c52, 0x2823),
                    new ConcreteDevice(0x0c52, 0x2833),
                    new ConcreteDevice(0x0c52, 0x2843),
                    new ConcreteDevice(0x0c52, 0x2853),
                    new ConcreteDevice(0x0c52, 0x2863),
                    new ConcreteDevice(0x0c52, 0x2873),
                    new ConcreteDevice(0x0c52, 0x2883),
                    new ConcreteDevice(0x0c52, 0xa02a),
                    new ConcreteDevice(0x0c52, 0xa02b),
                    new ConcreteDevice(0x0c52, 0xa02c),
                    new ConcreteDevice(0x0c52, 0xa02d),
                    new ConcreteDevice(0x0c6c, 0x04b2),
                    new ConcreteDevice(0x0c7d, 0x0005),
                    new ConcreteDevice(0x0d3a, 0x0300),
                    new ConcreteDevice(0x0d46, 0x2020),
                    new ConcreteDevice(0x0d46, 0x2021),
                    new ConcreteDevice(0x0dcd, 0x0001),
                    new ConcreteDevice(0x0f94, 0x0001),
                    new ConcreteDevice(0x0f94, 0x0005),
                    new ConcreteDevice(0x0fd8, 0x0001),
                    new ConcreteDevice(0x103e, 0x03e8),
                    new ConcreteDevice(0x104d, 0x3000),
                    new ConcreteDevice(0x104d, 0x3002),
                    new ConcreteDevice(0x104d, 0x3006),
                    new ConcreteDevice(0x1209, 0x1002),
                    new ConcreteDevice(0x1209, 0x1006),
                    new ConcreteDevice(0x128d, 0x0001),
                    new ConcreteDevice(0x1342, 0x0202),
                    new ConcreteDevice(0x1457, 0x5118),
                    new ConcreteDevice(0x15ba, 0x0003),
                    new ConcreteDevice(0x15ba, 0x002b),
                    new ConcreteDevice(0x1781, 0x0c30),
                    new ConcreteDevice(0x2100, 0x9001),
                    new ConcreteDevice(0x2100, 0x9e50),
                    new ConcreteDevice(0x2100, 0x9e51),
                    new ConcreteDevice(0x2100, 0x9e52),
                    new ConcreteDevice(0x2100, 0x9e53),
                    new ConcreteDevice(0x2100, 0x9e54),
                    new ConcreteDevice(0x2100, 0x9e55),
                    new ConcreteDevice(0x2100, 0x9e56),
                    new ConcreteDevice(0x2100, 0x9e57),
                    new ConcreteDevice(0x2100, 0x9e58),
                    new ConcreteDevice(0x2100, 0x9e59),
                    new ConcreteDevice(0x2100, 0x9e5a),
                    new ConcreteDevice(0x2100, 0x9e5b),
                    new ConcreteDevice(0x2100, 0x9e5c),
                    new ConcreteDevice(0x2100, 0x9e5d),
                    new ConcreteDevice(0x2100, 0x9e5e),
                    new ConcreteDevice(0x2100, 0x9e5f),
                    new ConcreteDevice(0x2100, 0x9e60),
                    new ConcreteDevice(0x2100, 0x9e61),
                    new ConcreteDevice(0x2100, 0x9e62),
                    new ConcreteDevice(0x2100, 0x9e63),
                    new ConcreteDevice(0x2100, 0x9e64),
                    new ConcreteDevice(0x2100, 0x9e65),
                    new ConcreteDevice(0x2100, 0x9e65),
                    new ConcreteDevice(0x2100, 0x9e66),
                    new ConcreteDevice(0x2100, 0x9e67),
                    new ConcreteDevice(0x2100, 0x9e68),
                    new ConcreteDevice(0x2100, 0x9e69),
                    new ConcreteDevice(0x2100, 0x9e6a),
                    new ConcreteDevice(0x0403, 0xe0a0),
                    new ConcreteDevice(0x0403, 0xe0a1),
                    new ConcreteDevice(0x1a72, 0x1000),
                    new ConcreteDevice(0x1a72, 0x1001),
                    new ConcreteDevice(0x1a72, 0x1002),
                    new ConcreteDevice(0x1a72, 0x1005),
                    new ConcreteDevice(0x1a72, 0x1007),
                    new ConcreteDevice(0x1a72, 0x1008),
                    new ConcreteDevice(0x1a72, 0x1009),
                    new ConcreteDevice(0x1a72, 0x100d),
                    new ConcreteDevice(0x1a72, 0x100e),
                    new ConcreteDevice(0x1a72, 0x100f),
                    new ConcreteDevice(0x1a72, 0x1011),
                    new ConcreteDevice(0x1a72, 0x1012),
                    new ConcreteDevice(0x1a72, 0x1013),
                    new ConcreteDevice(0x1a72, 0x1014),
                    new ConcreteDevice(0x1a72, 0x1015),
                    new ConcreteDevice(0x1a72, 0x1016),
                    new ConcreteDevice(0x165c, 0x0002),
                    new ConcreteDevice(0x1a79, 0x6001),
                    new ConcreteDevice(0x1b3d, 0x0100),
                    new ConcreteDevice(0x1b3d, 0x0101),
                    new ConcreteDevice(0x1b3d, 0x0102),
                    new ConcreteDevice(0x1b3d, 0x0103),
                    new ConcreteDevice(0x1b3d, 0x0104),
                    new ConcreteDevice(0x1b3d, 0x0105),
                    new ConcreteDevice(0x1b3d, 0x0106),
                    new ConcreteDevice(0x1b3d, 0x0107),
                    new ConcreteDevice(0x1b3d, 0x0108),
                    new ConcreteDevice(0x1b3d, 0x0109),
                    new ConcreteDevice(0x1b3d, 0x010a),
                    new ConcreteDevice(0x1b3d, 0x010b),
                    new ConcreteDevice(0x1b3d, 0x010c),
                    new ConcreteDevice(0x1b3d, 0x010d),
                    new ConcreteDevice(0x1b3d, 0x010e),
                    new ConcreteDevice(0x1b3d, 0x010f),
                    new ConcreteDevice(0x1b3d, 0x0110),
                    new ConcreteDevice(0x1b3d, 0x0111),
                    new ConcreteDevice(0x1b3d, 0x0112),
                    new ConcreteDevice(0x1b3d, 0x0113),
                    new ConcreteDevice(0x1b3d, 0x0114),
                    new ConcreteDevice(0x1b3d, 0x0115),
                    new ConcreteDevice(0x1b3d, 0x0116),
                    new ConcreteDevice(0x1b3d, 0x0117),
                    new ConcreteDevice(0x1b3d, 0x0118),
                    new ConcreteDevice(0x1b3d, 0x0119),
                    new ConcreteDevice(0x1b3d, 0x011a),
                    new ConcreteDevice(0x1b3d, 0x011b),
                    new ConcreteDevice(0x1b3d, 0x011c),
                    new ConcreteDevice(0x1b3d, 0x011d),
                    new ConcreteDevice(0x1b3d, 0x011e),
                    new ConcreteDevice(0x1b3d, 0x011f),
                    new ConcreteDevice(0x1b3d, 0x0120),
                    new ConcreteDevice(0x1b3d, 0x0121),
                    new ConcreteDevice(0x1b3d, 0x0122),
                    new ConcreteDevice(0x1b3d, 0x0123),
                    new ConcreteDevice(0x1b3d, 0x0124),
                    new ConcreteDevice(0x1b3d, 0x0125),
                    new ConcreteDevice(0x1b3d, 0x0126),
                    new ConcreteDevice(0x1b3d, 0x0127),
                    new ConcreteDevice(0x1b3d, 0x0128),
                    new ConcreteDevice(0x1b3d, 0x0129),
                    new ConcreteDevice(0x1b3d, 0x012a),
                    new ConcreteDevice(0x1b3d, 0x012b),
                    new ConcreteDevice(0x1b3d, 0x012c),
                    new ConcreteDevice(0x1b3d, 0x012e),
                    new ConcreteDevice(0x1b3d, 0x012f),
                    new ConcreteDevice(0x1b3d, 0x0130),
                    //Matrix orbital not all
                    new ConcreteDevice(0x1b91, 0x0064),
                    new ConcreteDevice(0x1bc9, 0x6001),
                    new ConcreteDevice(0x1c0c, 0x0102),
                    new ConcreteDevice(0x1cf1, 0x0001),
                    new ConcreteDevice(0x1cf1, 0x0041),
                    new ConcreteDevice(0x0483, 0x3746),
                    new ConcreteDevice(0x0483, 0x3747),
                    new ConcreteDevice(0x5050, 0x0100),
                    new ConcreteDevice(0x5050, 0x0101),
                    new ConcreteDevice(0x5050, 0x0102),
                    new ConcreteDevice(0x5050, 0x0103),
                    new ConcreteDevice(0x5050, 0x0104),
                    new ConcreteDevice(0x5050, 0x0105),
                    new ConcreteDevice(0x5050, 0x0106),
                    new ConcreteDevice(0x5050, 0x0107),
                    new ConcreteDevice(0x5050, 0x0300),
                    new ConcreteDevice(0x5050, 0x0301),
                    new ConcreteDevice(0x5050, 0x0400),
                    new ConcreteDevice(0x5050, 0x0500),
                    new ConcreteDevice(0x5050, 0x0700),
                    new ConcreteDevice(0x5050, 0x0800),
                    new ConcreteDevice(0x5050, 0x0900),
                    new ConcreteDevice(0x5050, 0x0a00),
                    new ConcreteDevice(0x5050, 0x0b00),
                    new ConcreteDevice(0x5050, 0x0c00),
                    new ConcreteDevice(0x5050, 0x0d00),
                    new ConcreteDevice(0x5050, 0x0e00),
                    new ConcreteDevice(0x5050, 0x0f00),
                    new ConcreteDevice(0x5050, 0x1000),
                    new ConcreteDevice(0x5050, 0x8000),
                    new ConcreteDevice(0x5050, 0x8001),
                    new ConcreteDevice(0x5050, 0x8002),
                    new ConcreteDevice(0x5050, 0x8003),
                    new ConcreteDevice(0x5050, 0x8004),
                    new ConcreteDevice(0x5050, 0x8005),
                    new ConcreteDevice(0x9e88, 0x9e8f),
                    new ConcreteDevice(0xdeee, 0x0300),
                    new ConcreteDevice(0xdeee, 0x02ff),
                    new ConcreteDevice(0xdeee, 0x0302),
                    new ConcreteDevice(0xdeee, 0x0303),
                    new ConcreteDevice(0x0403, 0x9378),
                    new ConcreteDevice(0x0403, 0x0379),
                    new ConcreteDevice(0x0403, 0x937a),
                    new ConcreteDevice(0x0403, 0x937c),
                    new ConcreteDevice(0x0403, 0x9868),
                    new ConcreteDevice(0x0403, 0xbca0),
                    new ConcreteDevice(0x0403, 0xbca1),
                    new ConcreteDevice(0x0403, 0xbca2),
                    new ConcreteDevice(0x0403, 0xbca4),
                    new ConcreteDevice(0x0403, 0xe729),
                    new ConcreteDevice(0x0403, 0xd578),
                    new ConcreteDevice(0x0403, 0xff18),
                    new ConcreteDevice(0x0403, 0xff1c),
                    new ConcreteDevice(0x0403, 0xff1d),
                    new ConcreteDevice(0x0403, 0x20b7),
                    new ConcreteDevice(0x0403, 0x0713),
                    new ConcreteDevice(0x0403, 0xf608),
                    new ConcreteDevice(0x0403, 0xf60b),
                    new ConcreteDevice(0x0403, 0xf7c0),
                    new ConcreteDevice(0x0403, 0x8a28),
                    new ConcreteDevice(0x0403, 0xa951),
                    new ConcreteDevice(0x0403, 0x8e08),
                    new ConcreteDevice(0x0403, 0x0011),
                    new ConcreteDevice(0x0403, 0x87d0),
                    new ConcreteDevice(0x05d1, 0x1001),
                    new ConcreteDevice(0x05d1, 0x1002),
                    new ConcreteDevice(0x05d1, 0x1003),
                    new ConcreteDevice(0x05d1, 0x1004),
                    new ConcreteDevice(0x05d1, 0x1011),
                    new ConcreteDevice(0x05d1, 0x1013),
                    new ConcreteDevice(0x05d1, 0x2001),
                    new ConcreteDevice(0x05d1, 0x2002),
                    new ConcreteDevice(0x05d1, 0x2003),
                    new ConcreteDevice(0x05d1, 0x2011),
                    new ConcreteDevice(0x05d1, 0x2012),
                    new ConcreteDevice(0x05d1, 0x2021),
                    new ConcreteDevice(0x05d1, 0x2022),
                    new ConcreteDevice(0x05d1, 0x2023),
                    new ConcreteDevice(0x05d1, 0x2024),
                    new ConcreteDevice(0x05d1, 0x3011),
                    new ConcreteDevice(0x05d1, 0x3012),
                    new ConcreteDevice(0x05d1, 0x5001),
                    new ConcreteDevice(0x05d1, 0x6001),
                    new ConcreteDevice(0x05d1, 0x7001),
                    new ConcreteDevice(0x05d1, 0x8001),
                    new ConcreteDevice(0x05d1, 0x8002),
                    new ConcreteDevice(0x05d1, 0x8003),
                    new ConcreteDevice(0x05d1, 0x8004),
                    new ConcreteDevice(0x05d1, 0x9001),
                    new ConcreteDevice(0x05d1, 0x9002),
                    new ConcreteDevice(0x05d1, 0x9003),
                    new ConcreteDevice(0x05d1, 0x9004),
                    new ConcreteDevice(0x05d1, 0x9005),
                    new ConcreteDevice(0x05d1, 0x9006),
                    new ConcreteDevice(0x05d1, 0x9007),
                    new ConcreteDevice(0x05d1, 0x9008),
            };

    public static boolean isDeviceSupported(int vendorId, int productId)
    {
        for(int i=0;i<=ftdiDevices.length-1;i++)
        {
            if(ftdiDevices[i].vendorId == vendorId && ftdiDevices[i].productId == productId )
            {
                return true;
            }
        }
        return false;
    }


    private static class ConcreteDevice
    {
        public int vendorId;
        public int productId;

        public ConcreteDevice(int vendorId, int productId)
        {
            this.vendorId = vendorId;
            this.productId = productId;
        }
    }

}

create table if not exists game_type
(
    id   integer not null
        constraint game_type_pk
            primary key,
    name varchar not null,
    type varchar not null
);

INSERT into game_type
values   (2073, 'acting', 'mechanic'), (2838, 'action-drafting', 'mechanic'), (2001, 'action-points', 'mechanic'), (2689, 'action-queue', 'mechanic'), (2839, 'action-retrieval', 'mechanic'), (2834, 'action-timer', 'mechanic'), (2840, 'actionevent', 'mechanic'), (2847, 'advantage-token', 'mechanic'), (2916, 'alliances', 'mechanic'), (2080, 'area-majority-influence', 'mechanic'), (2046, 'area-movement', 'mechanic'), (2021, 'area-impulse', 'mechanic'), (2012, 'auctionbidding', 'mechanic'), (2930, 'auction-dexterity', 'mechanic'), (2924, 'auction-dutch', 'mechanic'), (2932, 'auction-dutch-priority', 'mechanic'), (2918, 'auction-english', 'mechanic'), (2931, 'auction-fixed-placement', 'mechanic'), (2923, 'auction-once-around', 'mechanic'), (2920, 'auction-sealed-bid', 'mechanic'), (2919, 'auction-turn-order-until-pass', 'mechanic'), (2903, 'automatic-resource-growth', 'mechanic'), (2014, 'betting-and-bluffing', 'mechanic'), (2957, 'bias', 'mechanic'), (2999, 'bingo', 'mechanic'), (2913, 'bribery', 'mechanic'), (2018, 'campaign-battle-card-driven', 'mechanic'), (2041, 'card-drafting', 'mechanic'), (2857, 'card-play-conflict-resolution', 'mechanic'), (2887, 'catch-leader', 'mechanic'), (2956, 'chaining', 'mechanic'), (2057, 'chit-pull-system', 'mechanic'), (2928, 'closed-economy-auction', 'mechanic'), (2841, 'command-cards', 'mechanic'), (2013, 'commodity-speculation', 'mechanic'), (2893, 'communication-limits', 'mechanic'), (2883, 'connections', 'mechanic'), (2922, 'constrained-bidding', 'mechanic'), (2912, 'contracts', 'mechanic'), (2023, 'cooperative-game', 'mechanic'), (2010, 'crayon-rail-system', 'mechanic'), (2854, 'critical-hits-and-failures', 'mechanic'), (2990, 'cube-tower', 'mechanic'), (3004, 'deck-construction', 'mechanic'), (2664, 'deck-bag-and-pool-building', 'mechanic'), (3002, 'deduction', 'mechanic'), (2901, 'delayed-purchase', 'mechanic'), (2072, 'dice-rolling', 'mechanic'), (2856, 'die-icon-resolution', 'mechanic'), (2950, 'different-dice-movement', 'mechanic'), (2984, 'drafting', 'mechanic'), (2882, 'elapsed-real-time-ending', 'mechanic'), (2043, 'enclosure', 'mechanic'), (2875, 'end-game-bonuses', 'mechanic'), (2850, 'events', 'mechanic'), (2885, 'finale-ending', 'mechanic'), (2860, 'flicking', 'mechanic'), (2843, 'follow', 'mechanic'), (2864, 'force-commitment', 'mechanic'), (2978, 'grid-coverage', 'mechanic'), (2676, 'grid-movement', 'mechanic'), (2040, 'hand-management', 'mechanic'), (2026, 'hexagon-grid', 'mechanic'), (2967, 'hidden-movement', 'mechanic'), (2891, 'hidden-roles', 'mechanic'), (2987, 'hidden-victory-points', 'mechanic'), (2889, 'highest-lowest-scoring', 'mechanic'), (3000, 'hot-potato', 'mechanic'), (2906, 'i-cut-you-choose', 'mechanic'), (2952, 'impulse-movement', 'mechanic'), (2902, 'income', 'mechanic'), (2914, 'increase-value-unchosen-resources', 'mechanic'), (3003, 'induction', 'mechanic'), (2837, 'interrupts', 'mechanic'), (2910, 'investment', 'mechanic'), (2871, 'kill-steal', 'mechanic'), (2886, 'king-hill', 'mechanic'), (2980, 'ladder-climbing', 'mechanic'), (3001, 'layering', 'mechanic'), (2824, 'legacy-game', 'mechanic'), (2039, 'line-drawing', 'mechanic'), (2975, 'line-sight', 'mechanic'), (2904, 'loans', 'mechanic'), (2836, 'lose-turn', 'mechanic'), (2955, 'mancala', 'mechanic'), (2959, 'map-addition', 'mechanic'), (2961, 'map-deformation', 'mechanic'), (2960, 'map-reduction', 'mechanic'), (2900, 'market', 'mechanic'), (3007, 'matching', 'mechanic'), (2949, 'measurement-movement', 'mechanic'), (2981, 'melding-and-splaying', 'mechanic'), (2047, 'memory', 'mechanic'), (2863, 'minimap-resolution', 'mechanic'), (2011, 'modular-board', 'mechanic'), (2962, 'move-through-deck', 'mechanic'), (2947, 'movement-points', 'mechanic'), (2963, 'movement-template', 'mechanic'), (2958, 'moving-multiple-units', 'mechanic'), (2965, 'multiple-maps', 'mechanic'), (2927, 'multiple-lot-auction', 'mechanic'), (2851, 'narrative-choice-paragraph', 'mechanic'), (2915, 'negotiation', 'mechanic'), (2081, 'network-and-route-building', 'mechanic'), (2846, 'once-game-abilities','mechanic'), (2844, 'order-counters', 'mechanic'), (2911, 'ownership', 'mechanic'), (2055, 'paper-and-pencil', 'mechanic'), (2835, 'passed-action-token', 'mechanic'), (2048, 'pattern-building', 'mechanic'), (2946, 'pattern-movement', 'mechanic'), (2060, 'pattern-recognition', 'mechanic'), (2989, 'physical-removal', 'mechanic'), (2007, 'pick-and-deliver', 'mechanic'), (2964, 'pieces-map', 'mechanic'), (2685, 'player-elimination', 'mechanic'), (2865, 'player-judge', 'mechanic'), (2078, 'point-point-movement', 'mechanic'), (3006, 'predictive-bid', 'mechanic'), (2858, 'prisoners-dilemma', 'mechanic'), (2953, 'programmed-movement', 'mechanic'), (2661, 'push-your-luck', 'mechanic'), (2876, 'race', 'mechanic'), (2909, 'random-production', 'mechanic'), (2855, 'ratio-combat-results-table', 'mechanic'), (2870, 're-rolling-and-locking', 'mechanic'), (2831, 'real-time', 'mechanic'), (2954, 'relative-movement', 'mechanic'), (2948, 'resource-move', 'mechanic'), (2003, 'rock-paper-scissors', 'mechanic'), (2028, 'role-playing', 'mechanic'), (2892, 'roles-asymmetric-information', 'mechanic'), (2035, 'roll-spin-and-move', 'mechanic'), (2813, 'rondel', 'mechanic'), (2822, 'scenario-mission-campaign-game', 'mechanic'), (2823, 'score-and-reset-game', 'mechanic'), (2016, 'secret-unit-deployment', 'mechanic'), (2926, 'selection-order-bid', 'mechanic'), (2820, 'semi-cooperative-game', 'mechanic'), (2004, 'set-collection', 'mechanic'), (2070, 'simulation', 'mechanic'), (2020, 'simultaneous-action-selection', 'mechanic'), (2038, 'singing', 'mechanic'), (2821, 'single-loser-game', 'mechanic'), (3005, 'slidepush', 'mechanic'), (2819, 'solo-solitaire-game', 'mechanic'), (2991, 'speed-matching', 'mechanic'), (2940, 'square-grid', 'mechanic'), (2988, 'stacking-and-balancing', 'mechanic'), (2853, 'stat-check-resolution', 'mechanic'), (2861, 'static-capture', 'mechanic'), (2005, 'stock-holding', 'mechanic'), (2027, 'storytelling', 'mechanic'), (2884, 'sudden-death-ending', 'mechanic'), (2686, 'take', 'mechanic'), (2866, 'targeted-clues', 'mechanic'), (2019, 'team-based-game', 'mechanic'), (2849, 'tech-trees-tech-tracks', 'mechanic'), (2944, 'three-dimensional-movement', 'mechanic'), (2002, 'tile-placement', 'mechanic'), (2663, 'time-track', 'mechanic'), (2939, 'track-movement', 'mechanic'), (2008, 'trading', 'mechanic'), (2814, 'traitor-game', 'mechanic'), (2009, 'trick-taking', 'mechanic'), (2888, 'tug-war', 'mechanic'), (2827, 'turn-order-auction', 'mechanic'), (2829, 'turn-order-claim-action', 'mechanic'), (2830, 'turn-order-pass-order', 'mechanic'), (2828, 'turn-order-progressive', 'mechanic'), (2985, 'turn-order-random', 'mechanic'), (2833, 'turn-order-role-order', 'mechanic'), (2826, 'turn-order-stat-based', 'mechanic'), (2079, 'variable-phase-order', 'mechanic'), (2015, 'variable-player-powers', 'mechanic'), (2897, 'variable-set', 'mechanic'), (2874, 'victory-points-resource', 'mechanic'), (2017, 'voting', 'mechanic'), (2082, 'worker-placement', 'mechanic'), (2935, 'worker-placement-dice-workers', 'mechanic'), (2933, 'worker-placement-different-worker-types', 'mechanic'), (2974, 'zone-control', 'mechanic');

INSERT into game_type
values   (1009, 'abstract-strategy', 'category'),(1032, 'action-dexterity', 'category'),(1022, 'adventure', 'category'),(2726, 'age-reason', 'category'),(1048, 'american-civil-war', 'category'),(1108, 'american-indian-wars', 'category'),(1075, 'american-revolutionary-war', 'category'),(1055, 'american-west', 'category'),(1050, 'ancient', 'category'),(1089, 'animals', 'category'),(1052, 'arabian', 'category'),(2650, 'aviation-flight', 'category'),(1023, 'bluffing', 'category'),(1117, 'book', 'category'),(1002, 'card-game', 'category'),(1041, 'childrens-game', 'category'),(1029, 'city-building', 'category'),(1102, 'civil-war', 'category'),(1015, 'civilization', 'category'),(1044, 'collectible-components', 'category'),(1116, 'comic-book-strip', 'category'),(1039, 'deduction', 'category'),(1017, 'dice', 'category'),(1021, 'economic', 'category'),(1094, 'educational', 'category'),(1072, 'electronic', 'category'),(1084, 'environmental', 'category'),(1042, 'expansion-base-game', 'category'),(1020, 'exploration', 'category'),(2687, 'fan-expansion', 'category'),(1010, 'fantasy', 'category'),(1013, 'farming', 'category'),(1046, 'fighting', 'category'),(1119, 'game-system', 'category'),(1024, 'horror', 'category'),(1079, 'humor', 'category'),(1088, 'industry-manufacturing', 'category'),(1091, 'korean-war', 'category'),(1033, 'mafia', 'category'),(1104, 'math', 'category'),(1118, 'mature-adult', 'category'),(1059, 'maze', 'category'),(2145, 'medical', 'category'),(1035, 'medieval', 'category'),(1045, 'memory', 'category'),(1047, 'miniatures', 'category'),(1069, 'modern-warfare', 'category'),(1064, 'movies-tv-radio-theme', 'category'),(1040, 'murdermystery', 'category'),(1054, 'music', 'category'),(1082, 'mythology', 'category'),(1051, 'napoleonic', 'category'),(1008, 'nautical', 'category'),(1026, 'negotiation', 'category'),(1093, 'novel-based', 'category'),(1098, 'number', 'category'),(1030, 'party-game', 'category'),(2725, 'pike-and-shot', 'category'),(1090, 'pirates', 'category'),(1001, 'political', 'category'),(2710, 'post-napoleonic', 'category'),(1036, 'prehistoric', 'category'),(1120, 'print-play', 'category'),(1028, 'puzzle', 'category'),(1031, 'racing', 'category'),(1037, 'real-time', 'category'),(1115, 'religious', 'category'),(1070, 'renaissance', 'category'),(1016, 'science-fiction', 'category'),(1113, 'space-exploration', 'category'),(1081, 'spiessecret-agents', 'category'),(1038, 'sports', 'category'),(1086, 'territory-building', 'category'),(1034, 'trains', 'category'),(1011, 'transportation', 'category'),(1097, 'travel', 'category'),(1027, 'trivia', 'category'),(1101, 'video-game-theme', 'category'),(1109, 'vietnam-war', 'category'),(1019, 'wargame', 'category'),(1025, 'word-game', 'category'),(1065, 'world-war-i', 'category'),(1049, 'world-war-ii', 'category'),(2481, 'zombies', 'category')

# Fortress-Defense

Similar to BattleShip, Fortress Defense consist of the idea of a large field and it is your job to discover the enemies tanks as its attacking you at the same time.
The game can be loaded up on localhost with Spring Boot and using REST API calls. 

Game Description
The game is played with enemy tanks located somewhere within a 10 by 10 grid of cells. Each turn,
you get to fire your gun, and then the enemy fires their guns (one per tank). They start off with N tanks
(set by command line argument); each tank occupies five connected cells forming a polyomino (any
randomly constructed one; different tanks may be different patterns). Once the enemy tanks are placed,
they do not move (they are siege tanks, after all!).

Your fortress can take 2500 points of structural damage before collapsing, at which point you lose the
game. The amount of damage that a siege tank does is relative to how many undamaged cells it has:

Undamaged Tank Cells: 5 4 3 2 1 0
Damage Done:          20 20 5 2 1 0

Note that even if the middle of a tank is damaged, it still counts as one tank, and continues damaging
the fortress, as listed in the table. (These things are resilient!)

You cannot see the battle field due to the think fog, but your gunner can hear when your shot hits a
tank. Therefore, your gunner creates a map of where you have fired, and where you have hit an enemy
tank. Each turn you can see your fortress' remaining structural strength, the battle-field map, and how
much damage your fortress took during the previous turn.


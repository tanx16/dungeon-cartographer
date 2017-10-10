from random import randint
wall = '#'
class Map:
    self.dungeon = []
    def __init__(self, width, height, numRoomandCorridors):
        self.width = width
        self.height = height
        for w in range(width):
            self.dungeon.append([])
            for h in range(height):
                self.dungeon[w].append(wall)
        makeDungeon(self, numRoomandCorridors)
    
    def makeCorridor(self, x, y, maxlen, direction=0):
        length = randint(2, maxlen)
    def makeRoom(self, x, y, xlen, ylen, direction=0):
        return False
    def makeDungeon(self, numFeatures):
        self.makeRoom(self.width//2, self.height//2, 8, 6, randint(0, 4))
        numTries=0
        while numTries>1000:
            print('Under Construction')
            numTries+=10
        return

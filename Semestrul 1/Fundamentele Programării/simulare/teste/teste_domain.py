from domain.haine import Haina
class TestDomain:
    def __init__(self):
        pass

    def ruleaza_teste():
        geaca = Haina(1, 'Haine munte SRL', 'geaca', 100)
        assert (geaca.get_id() == 1)
        assert (geaca.get_fabricant()== 'Haine munte SRL')
        assert (geaca.get_tip() == 'geaca')
        assert (geaca.get_pret() == 100)


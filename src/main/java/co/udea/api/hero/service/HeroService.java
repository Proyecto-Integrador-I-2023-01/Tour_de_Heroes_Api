package co.udea.api.hero.service;

import co.udea.api.hero.exception.BusinessException;
import co.udea.api.hero.model.Hero;

import co.udea.api.hero.repository.HeroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HeroService {

    private final Logger log = LoggerFactory.getLogger(HeroService.class);

    private HeroRepository heroRepository;



    public HeroService(HeroRepository heroRepository){
        this.heroRepository = heroRepository;

    }

    public Hero getHero(Integer id){
        Optional<Hero> optionalHero = heroRepository.findById(id);
        if(!optionalHero.isPresent()){
            log.info("No se encuentra un heroe con ID: "+id);
            throw new BusinessException("El Héroe no existe. ");
        }
        return optionalHero.get();
    }

    public List<Hero> getHeroes(){
        List<Hero> heroesList = heroRepository.findAll();
        if(heroesList.isEmpty()){
            log.info("No se encuentran heroes en la base de datos");
            throw new BusinessException("Los Héroes  no existen. ");
        }
        return heroesList;
    }
    public List<Hero> searchHeroes(String name){
        List<Hero> heroes = heroRepository.findByNameContaining(name);
        if(heroes.isEmpty()){
            log.info("No se encuentra un heroe con nombre :"+name);
            throw new BusinessException("No hay mensaje disponible para mostrar.");
        }
        return heroes;
    }
    public Hero updateHero(Hero hero){
        Optional<Hero> optionalHero = heroRepository.findById(hero.getId());
        if(!optionalHero.isPresent()){
            log.info("No se encuentra un heroe registrado con ID: "+hero.getId());
            throw new BusinessException("No hay mensaje disponible para mostrar.");
        }
        optionalHero.get().setName(hero.getName());
        return heroRepository.save(optionalHero.get());
    }

    public Hero addHero(Hero hero){


        return heroRepository.save(hero);
    }

    public void deleteHero(Integer id){
        Optional<Hero> optionalHero = heroRepository.findById(id);
        if(!optionalHero.isPresent()){
            log.info("No se encuentra un heroe con ID:"+id);
            throw new BusinessException("No hay mensaje disponible para mostrar.");
        }
        heroRepository.delete(optionalHero.get());
    }



}

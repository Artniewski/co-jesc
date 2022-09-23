package com.ocado.cojesc.service;

import com.ocado.cojesc.client.ScraperFeignClient;
import com.ocado.cojesc.parser.FacebookPost;
import com.ocado.cojesc.restaurant.Restaurant;
import com.ocado.cojesc.restaurant.RestaurantsProvider;
import com.ocado.cojesc.validator.FacebookPostValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@Service
public class CacheAwareFacebookRestaurantService {

    private static final String CACHE_NAME = "lunch-menu";

    private final ScraperFeignClient fbClient;
    private final FacebookPostValidator facebookPostValidator;
    private final ExecutorService executorService;

    public CacheAwareFacebookRestaurantService(ScraperFeignClient scraperFeignClient, FacebookPostValidator facebookPostValidator, RestaurantsProvider restaurantsProvider) {
        this.fbClient = scraperFeignClient;
        this.facebookPostValidator = facebookPostValidator;
        this.executorService = Executors.newFixedThreadPool(restaurantsProvider.getRestaurants().size());
    }

    @Cacheable(cacheNames = {CACHE_NAME}, key = "#restaurant.name", unless = "#result == null")
    public Future<Optional<FacebookPost>> findNewestMenuPost(Restaurant restaurant) {
        return executorService.submit(() -> {
            log.info("Menu for {} restaurant not found in cache. Scraping from FB.", restaurant.getName());

//        TODO: remove mocks menu and use scraper
            List<String> facebookPostsAsString = mockMenuResponse();
//        List<String> facebookPostsAsString = fbClient.getPosts(restaurant.getFacebookId());

            return facebookPostsAsString.stream()
                    .map(post -> FacebookPost.parse(restaurant.getFacebookId(), post))
                    .filter(facebookPost -> facebookPostValidator.validate(facebookPost, restaurant))
                    .max(Comparator.naturalOrder());
        });
    }

    @Scheduled(cron = "${cojesc.cache.eviction}")
    @CacheEvict(cacheNames = {CACHE_NAME}, allEntries = true)
    public void clearCache() {
        log.info("{} cache evicted", CACHE_NAME);
    }

    private List<String> mockMenuResponse() throws InterruptedException {
        Thread.sleep(3000);
        return List.of(
                "Pod Latarniami\n19 September at 11:23\n \n · \nZaczynamy nowy tydzień \nPoniedziałek\nOgórkowa z koperkiem i śmietaną \nWolno pieczona łopatka z majerankiem i suszonymi morelami, ziemniaki hasselback z tartym serem gruyere, wrześniowa surówka z tartymi burakami\nWtorek\nMinestrone z grissini i serem Pecorino Romano\nKlopsiki w sosie pomidorowym ze świeżym oregano, kremowe puree ziemniaczane, surówka z marchewki, selera i pora\nŚroda\nKrem z pieczonej papryki i pomidorów ze słonym serem krowim\nKanapka z kotletem schabowym, mizeria ze śmietaną, koperek, jajko sadzone, sałata masłowa, frytki ze skórką, dip z pieczonego czosnku\nCzwartek\nKhao Poon - regionalna zupa z Laosu z mleczkiem kokosowym \nLarb - laotańskie narodowe danie - kurczak z kiełkami, cebulką, chilli, kaffirem i miętą, kao niao (kleisty ryż) i prażonymi orzeszkami ziemnymi\nPiątek\nKrem z dynii z prażonymi pestkami i creme fraiche\nFish and Chips (Ryba w cieście piwnym, frytki w przyprawach cajun, zielona fasolka z masełkiem, sos tatarski z białą cebulką)\nLike\nComment\nShare\n0 comments",
                "Pod Latarniami\n20 September at 11:23\n \n · \nZaczynamy nowy tydzień \nPoniedziałek\nOgórkowa z koperkiem i śmietaną \nWolno pieczona łopatka z majerankiem i suszonymi morelami, ziemniaki hasselback z tartym serem gruyere, wrześniowa surówka z tartymi burakami\nWtorek\nMinestrone z grissini i serem Pecorino Romano\nKlopsiki w sosie pomidorowym ze świeżym oregano, kremowe puree ziemniaczane, surówka z marchewki, selera i pora\nŚroda\nKrem z pieczonej papryki i pomidorów ze słonym serem krowim\nKanapka z kotletem schabowym, mizeria ze śmietaną, koperek, jajko sadzone, sałata masłowa, frytki ze skórką, dip z pieczonego czosnku\nCzwartek\nKhao Poon - regionalna zupa z Laosu z mleczkiem kokosowym \nLarb - laotańskie narodowe danie - kurczak z kiełkami, cebulką, chilli, kaffirem i miętą, kao niao (kleisty ryż) i prażonymi orzeszkami ziemnymi\nPiątek\nKrem z dynii z prażonymi pestkami i creme fraiche\nFish and Chips (Ryba w cieście piwnym, frytki w przyprawach cajun, zielona fasolka z masełkiem, sos tatarski z białą cebulką)\nLike\nComment\nShare\n0 comments",
                "Pod Latarniami\n18 September at 11:23\n \n · \nZaczynamy nowy tydzień \nPoniedziałek\nOgórkowa z koperkiem i śmietaną \nWolno pieczona łopatka z majerankiem i suszonymi morelami, ziemniaki hasselback z tartym serem gruyere, wrześniowa surówka z tartymi burakami\nWtorek\nMinestrone z grissini i serem Pecorino Romano\nKlopsiki w sosie pomidorowym ze świeżym oregano, kremowe puree ziemniaczane, surówka z marchewki, selera i pora\nŚroda\nKrem z pieczonej papryki i pomidorów ze słonym serem krowim\nKanapka z kotletem schabowym, mizeria ze śmietaną, koperek, jajko sadzone, sałata masłowa, frytki ze skórką, dip z pieczonego czosnku\nCzwartek\nKhao Poon - regionalna zupa z Laosu z mleczkiem kokosowym \nLarb - laotańskie narodowe danie - kurczak z kiełkami, cebulką, chilli, kaffirem i miętą, kao niao (kleisty ryż) i prażonymi orzeszkami ziemnymi\nPiątek\nKrem z dynii z prażonymi pestkami i creme fraiche\nFish and Chips (Ryba w cieście piwnym, frytki w przyprawach cajun, zielona fasolka z masełkiem, sos tatarski z białą cebulką)\nLike\nComment\nShare\n0 comments",
                "Pod Latarniami\n12 September at 11:23\n \n · \nZaczynamy nowy tydzień \nPoniedziałek\nOgórkowa z koperkiem i śmietaną \nWolno pieczona łopatka z majerankiem i suszonymi morelami, ziemniaki hasselback z tartym serem gruyere, wrześniowa surówka z tartymi burakami\nWtorek\nMinestrone z grissini i serem Pecorino Romano\nKlopsiki w sosie pomidorowym ze świeżym oregano, kremowe puree ziemniaczane, surówka z marchewki, selera i pora\nŚroda\nKrem z pieczonej papryki i pomidorów ze słonym serem krowim\nKanapka z kotletem schabowym, mizeria ze śmietaną, koperek, jajko sadzone, sałata masłowa, frytki ze skórką, dip z pieczonego czosnku\nCzwartek\nKhao Poon - regionalna zupa z Laosu z mleczkiem kokosowym \nLarb - laotańskie narodowe danie - kurczak z kiełkami, cebulką, chilli, kaffirem i miętą, kao niao (kleisty ryż) i prażonymi orzeszkami ziemnymi\nPiątek\nKrem z dynii z prażonymi pestkami i creme fraiche\nFish and Chips (Ryba w cieście piwnym, frytki w przyprawach cajun, zielona fasolka z masełkiem, sos tatarski z białą cebulką)\nLike\nComment\nShare\n0 comments",
                "Pod Latarniami\n11 September at 12:34\n \n · Instagram\n \n · \nNiedzielny obiad ?\nNasz klasyk - Kotlet schabowy, puree ziemniaczane, mizeria \n#niedzielnyobiad #podlatarniami #wroclawskiejedzenie\n2\nLike\nComment\nShare\n0 comments",
                "Pod Latarniami\n5 September at 10:37\n \n · \nOto nasze propozycje na najbliższy tydzień \nPoniedziałek\nKrem pomidorowy z mascarpone ziołowym\nFilet z kurczaka Caprese - zapiekany z mozzarellą i pomidorem, świeża bazylia, łódeczki ziemniaczane z oregano, sałata lodowa z czerwoną cebulą, pomidorkami i oliwkami\nWtorek\nBurgoo - tradycyjna zupa z Kentucky\nŻeberka wieprzowe w glazurze BBQ, frytki stekowe, ranch dressing, grillowana kolba kukurydzy, colesław z porem \nŚroda\nTom Yum z mleczkiem kokosowym, makaronem ryżowym i grzybami shitake\nSkrzydełka z kurczaka w glazurze Teriyaki, prażony sezam, ryż jaśminowy z liśćmi kafiru, kolendra, kapusta pekińska z marynowanym imbirem i chili\nCzwartek\nŻurek z białą kiełbasą i jajkiem\nBitki wieprzowe w sosie grzybowym, kluseczki ziemniaczane, modra kapusta z żurawiną\nPiątek\nKrem z cukinii z prażonymi ziarnami i tartą białą czekoladą\nKrokiety rybne z morszczuka, ziemniaki z koperkiem i palonym masłem, sos tatarski, limonka, konfitowana marchewka z tymiankiem\n1\nLike\nComment\nShare\n0 comments",
                "Pod Latarniami\n2 September at 19:12\n \n · Instagram\n \n · \nPiątkowy wieczór to idealna okazja aby spróbować nowości w naszym menu \nZapraszamy!!!\n3\nLike\nComment\nShare\n0 comments",
                "Pod Latarniami\n1 September at 16:25\n \n · Instagram\n \n · \nWrzesień nie taki straszny \n \nRozpocznijmy go jak należy!!!\nBacardi Spiced & Cola w konfiguracji 1+1 gratis brzmi jak plan na wieczór!\n#podlatarniami #bacardispiced \n#dowhatmovesyou\n6\n2 shares\nLike\nComment\nShare\n0 comments",
                "Pod Latarniami\n29 August at 11:48\n \n · \nNowy tydzień - nowe lunche \nPoniedziałek\nOgórkowa z kwaśną śmietaną i koperkiem\nPanierowany filet z kurczaka, pieczone ziemniaki z oliwą ziołową, surówka z kiszonej kapusty\nWtorek\nJarzynowa z kalafiorem i fasolką szparagową\nSchab duszony z pieczarkami i cebulką, puree ziemniaczane z suszonymi pomidorami, modra kapusta z jabłkiem\nŚroda\nPomidorowa z makaronem\nKnysza wrocławska z kotletem schabowym, czerwoną kapustą, pomidorem, prażoną cebulką, jogurtem miętowym, frytkami i sosem chrzanowym\nCzwartek\nKrem z borowików z grzankami i śmietanką\nFish Pie - zapiekanka ziemniaczana z rybą, beszamelem, serem cheddar, kremowy zielony groszek\nPiątek\nZupa piwna z grzanką serową\nPanierowany ser edam, sos tatarski, konfitura z żurawiny, talarki ziemniaczane, colesław z grillowaną śliwką\n1\nLike\nComment\nShare\n0 comments",
                "Pod Latarniami\n22 August\n \n · \nWitamy w nowym tygodniu \n \nPoniedziałek\nKapuśniak\nWolno pieczona karkówka, BBQ wild cherry bourbon, kluski ziemniaczane, pieczony kalafior z palonym masłem\nWtorek\nBarszcz ukraiński z kwaśna śmietana i szczypiorkiem \nPlacki ziemniaczane z gulaszem z grzybami, sałatka z kiszonek, śmietana\nŚroda \nRosół wołowy z makaronem ryżowym i glonami wakame \nPanierowany filet z kurczaka, ryż jasminowy, sos Teriyaki, sałatka z białą rzepą\nCzwartek\nŻurek z biała kiełbasa i jajkiem \nKotlet schabowy, jajko sadzone, koperek, puree ziemniaczane, mizeria z ogórków szklarniowych z kefirem\nPiątek\nKrem z pieczonych warzyw z grzankami ziołowymi\nMakaron z poledwiczką wieprzową, kremowy sos z kurek leśnych, tymianek, Pieteuszka, ser Grana Padano\n1\nLike\nComment\nShare\n0 comments",
                "Pod Latarniami\n18 August\n \n · Instagram\n \n · \nJuż jutro zapraszamy na wieczór z Jack Daniels Apple \nDodatkowo możecie odebrać czapke za dwa \n na bazie Jack'a \n#jackdaniels #jackdanielsapple #zgarnijczape\n9\nLike\nComment\nShare\n0 comments",
                "Pod Latarniami\n16 August\n \n · \nNasze lunche w tym tygodniu\nWtorek\nKrem pomidorowy z serem mozzarella \nKotlet schabowy w panko, frytki z wędzona papryka, mizeria z ogórków gruntowych, rzodkiewki i kefiru … See more\n1\nLike\nComment\nShare\n0 comments",
                "Pod Latarniami\n8 August\n \n · \nWitamy w nowym tygodniu \nPoniedziałek\nKrem z buraka z białym serem i śmietanką\nŻeberko wieprzowe w sosie z portera truskawkowego, młode ziemniaki podsmażane z boczkiem, sałatka ze świeżego ogórka i pomidora… See more\n2\n1 share\nLike\nComment\nShare\n0 comments"
        );
    }
}

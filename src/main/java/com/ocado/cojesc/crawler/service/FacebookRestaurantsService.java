package com.ocado.cojesc.crawler.service;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.LoadState;
import com.ocado.cojesc.FacebookProperties;
import com.ocado.cojesc.crawler.FacebookPost;
import com.ocado.cojesc.crawler.utils.SelectorGenerator;
import com.ocado.cojesc.crawler.validator.LunchPostValidator;
import com.ocado.cojesc.restaurant.Restaurant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FacebookRestaurantsService {

    private final LunchPostValidator lunchPostValidator;
    private final FacebookProperties fbProperties;

    public List<FacebookPost> getAllPosts(List<Restaurant> restaurants) {
        ForkJoinPool pool = new ForkJoinPool(restaurants.size());
        return pool.submit(() -> restaurants.parallelStream()
                .map(this::getLunchPosts)
                .flatMap(Collection::stream)
                .toList()).join();
    }

    public List<FacebookPost> getLunchPosts(Restaurant restaurant) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

        BrowserContext ctx = browser.newContext(new Browser.NewContextOptions()
                .setStorageStatePath(Paths.get("state.json"))
                .setBaseURL(fbProperties.getBaseUrl())
                .setUserAgent("Chrome/55.0.2883.91")
                .setLocale("pl-PL")
                .setViewportSize(1000, 3000));
        Page page = ctx.newPage();

        List<FacebookPost> allFacebookPosts = getFacebookPosts(page, restaurant);

        List<FacebookPost> lunchPosts = lunchPostValidator.getLunchPosts(restaurant, allFacebookPosts);
        page.waitForTimeout(10000);
        playwright.close();
        return lunchPosts;
    }

    private List<FacebookPost> getFacebookPosts(Page page, Restaurant restaurant) {
        String facebookId = restaurant.getFacebookId();
        page.navigate(facebookId);
//        page.waitForTimeout(1000);
        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.mouse().wheel(0, 3000);
        page.waitForTimeout(1000);
        List<ElementHandle> buttons = page.querySelectorAll(fbProperties.getSeeMoreSelector());
        buttons.forEach(button -> {
            if (button.isVisible()) {
                button.click();
            }
        });
        System.out.println(buttons.size());

//        for (int i = 0; i < 4; i++) {
//            page.click(fbProperties.getSeeMoreSelector());
////            page.mouse().wheel(0, 500);
//        }

        String postSelector = SelectorGenerator.generateSelectorFromClasses(restaurant.getFacebookPostClasses());
//        Locator postLocator  = page.locator(postSelector);

        List<ElementHandle> posts = page.querySelectorAll(postSelector);
        List<FacebookPost> allFacebookPosts = new ArrayList<>();
        for (ElementHandle post : posts) {
            FacebookPost facebookPost = FacebookPost.builder()
                    .facebookId(facebookId)
                    .innerHTML(post.innerHTML())
                    .innerText(post.innerText())
                    .build();
            allFacebookPosts.add(facebookPost);
        }

//        List<FacebookPost> allFacebookPosts = new ArrayList<>();
//
//        for (int i = 0; i < postLocator.count(); i++) {
//            String postInnerHTML = postLocator.nth(i).innerHTML();
//            String postInnerText = postLocator.nth(i).innerText();
//            FacebookPost facebookPost = FacebookPost.builder()
//                    .facebookId(facebookId)
//                    .innerHTML(postInnerHTML)
//                    .innerText(postInnerText)
//                    .build();
//            allFacebookPosts.add(facebookPost);
//        }
        return allFacebookPosts;
    }

    public void login() {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));
        BrowserContext ctx = browser.newContext(new Browser.NewContextOptions()
                .setBaseURL(fbProperties.getBaseUrl())
                .setUserAgent("Chrome/55.0.2883.91")
                .setLocale("pl-PL"));
        Page page = ctx.newPage();
        page.navigate("https://facebook.com/login");
        page.click(fbProperties.getCookieButtonSelector());

// Interact with login form
        page.locator("input[name='email']").fill("cojescapp@gmail.com");
        page.locator("input[name='pass']").fill("QW!58piCj+%hy#/");
        page.locator("button[name='login']").click();
// Verify app is logged in
        // Save storage state into the file.
        page.waitForTimeout(30000);
        ctx.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("state.json")));
        playwright.close();
    }

}

package com.kakz.tradeIn.controller;

import com.kakz.tradeIn.model.Asset;
import com.kakz.tradeIn.model.User;
import com.kakz.tradeIn.service.AssetService;
import com.kakz.tradeIn.service.UserService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The AssetController class is a REST controller for managing assets.
 * It provides endpoints for retrieving asset information based on asset ID,
 * coin ID, and user authentication.
 */
@RestController
@RequestMapping("/api/assets")
public class AssetController {
    /**
     * Service for performing operations on assets.
     * Provides methods to create, retrieve, update, and delete assets,
     * as well as to find assets based on user and coin identifiers.
     */
    private final AssetService assetService;
    /**
     * Service for managing user-related operations.
     *
     * This service provides methods to handle user profile retrieval, user authentication,
     * password updates, and enabling two-factor authentication.
     */
    @Autowired
    private UserService userService;

    /**
     * Constructs an AssetController with the provided AssetService.
     *
     * @param assetService the asset service to be used by the controller
     */
    @Autowired
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    /**
     * Retrieves an asset by its ID.
     *
     * @param assetId the ID of the asset to retrieve
     * @return a ResponseEntity containing the requested asset
     * @throws Exception if an error occurs during the retrieval process
     */
    @GetMapping("/{assetId}")
    public ResponseEntity<Asset> getAssetById(@PathVariable Long assetId) throws Exception {
        Asset asset = assetService.getAssetById(assetId);
        return ResponseEntity.ok().body(asset);
    }

    /**
     * Retrieves an asset associated with a specified user and coin.
     *
     * @param coinId the ID of the coin to retrieve the asset for, from path variable.
     * @param jwt the JSON Web Token used to authenticate the user, from the request header.
     * @return the ResponseEntity containing the asset corresponding to the given user and coin IDs.
     * @throws Exception if an error occurs while retrieving the asset or user profile.
     */
    @GetMapping("/coin/{coinId}/user")
    public ResponseEntity<Asset> getAssetByUserIdAndCoinId(
            @PathVariable String coinId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user=userService.findUserProfileByJwt(jwt);
        Asset asset = assetService.findAssetByUserIdAndCoinId(user.getId(), coinId);
        return ResponseEntity.ok().body(asset);
    }

    /**
     * Retrieves the list of assets for an authenticated user.
     *
     * @param jwt the JSON Web Token used for authenticating the user
     * @return a ResponseEntity containing a list of Assets associated with the user
     * @throws Exception if an error occurs while processing the request
     */
    @GetMapping()
    public ResponseEntity<List<Asset>> getAssetsForUser(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        List<Asset> assets = assetService.getUsersAssets(user.getId());
        return ResponseEntity.ok().body(assets);
    }
}

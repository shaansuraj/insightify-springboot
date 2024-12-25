package com.example.insightify.security; // Specifies the package for the class, typically security-related.

import java.util.Date; // Imports the Date class to work with expiration dates for the token.

import org.springframework.beans.factory.annotation.Value; // Imports the annotation to inject values from application properties.
import org.springframework.stereotype.Component; // Imports the Component annotation to make this class a Spring Bean.

import io.jsonwebtoken.Claims; // Imports the Claims class used for parsing JWT claims.
import io.jsonwebtoken.Jwts; // Imports the Jwts utility class for building and parsing JWT tokens.
import io.jsonwebtoken.SignatureAlgorithm; // Imports the SignatureAlgorithm class to specify the algorithm used to sign the JWT.
import io.jsonwebtoken.io.Decoders; // Imports Decoders to decode the secret key from Base64 format.
import io.jsonwebtoken.security.Keys; // Imports Keys to create a signing key for the JWT.

@Component // Marks this class as a Spring Component, allowing it to be autowired into other classes as a bean.
public class JwtTokenProvider {

    @Value("${app.jwtSecret}") // Injects the secret key for JWT signing, retrieved from the application properties file.
    private String jwtSecret; // Stores the JWT secret key, used for signing and validating tokens.

    @Value("${app.jwtExpirationInMs}") // Injects the expiration time for JWT tokens (in milliseconds).
    private int jwtExpirationInMs; // Stores the expiration time for the token.

    // Method to generate a JWT token containing username, role, and userId in its payload.
    public String generateToken(String username, String role, Long userId) {
        Date now = new Date(); // Gets the current date and time.
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs); // Sets the expiration date of the token based on the current time and configured expiration time.

        return Jwts.builder() // Begins the process of building the JWT token.
                .setSubject(username) // Sets the "sub" claim to store the username (subject).
                .claim("role", role) // Adds a custom claim "role" to the payload (e.g., "ROLE_USER").
                .claim("userId", userId) // Adds a custom claim "userId" to the payload to store the user's ID.
                .setIssuedAt(now) // Sets the "iat" claim to indicate when the token was issued.
                .setExpiration(expiryDate) // Sets the "exp" claim to indicate when the token will expire.
                .signWith(
                    Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)), // Signs the token with the secret key, using HMAC-SHA-512 algorithm.
                    SignatureAlgorithm.HS512 // Specifies the signing algorithm to use (HMAC SHA-512).
                )
                .compact(); // Builds and returns the JWT token as a compact string.
    }

    // Method to validate the JWT token by checking its signature and expiration.
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder() // Creates a JWT parser with the specified configuration.
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))) // Sets the signing key for validation.
                .build() // Builds the parser.
                .parseClaimsJws(token); // Parses the JWT token to validate its signature and claims.
            return true; // Returns true if the token is valid (i.e., signature is correct and not expired).
        } catch (Exception e) { // Catches any exception that occurs during parsing or validation.
            return false; // Returns false if the token is invalid or has expired.
        }
    }

    // Method to extract the username (subject) from the JWT token.
    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder() // Creates a JWT parser with the specified configuration.
            .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))) // Sets the signing key for validation.
            .build() // Builds the parser.
            .parseClaimsJws(token) // Parses the JWT token to extract claims.
            .getBody(); // Retrieves the body of the JWT (contains the claims).
        return claims.getSubject(); // Returns the subject (username) from the claims.
    }

    // Optional helper method to extract the userId from the JWT token.
    public Long getUserId(String token) {
        Claims claims = Jwts.parserBuilder() // Creates a JWT parser with the specified configuration.
            .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))) // Sets the signing key for validation.
            .build() // Builds the parser.
            .parseClaimsJws(token) // Parses the JWT token to extract claims.
            .getBody(); // Retrieves the body of the JWT (contains the claims).
        return claims.get("userId", Long.class); // Returns the userId claim from the JWT.
    }

    // Method to extract the role from the JWT token.
    public String getRole(String token) {
        Claims claims = Jwts.parserBuilder() // Creates a JWT parser with the specified configuration.
            .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))) // Sets the signing key for validation.
            .build() // Builds the parser.
            .parseClaimsJws(token) // Parses the JWT token to extract claims.
            .getBody(); // Retrieves the body of the JWT (contains the claims).
        return claims.get("role", String.class); // Returns the role claim from the JWT.
    }
}

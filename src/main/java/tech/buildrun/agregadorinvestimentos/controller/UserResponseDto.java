package tech.buildrun.agregadorinvestimentos.controller;

import java.util.List;

public record UserResponseDto(String userId, String username, String email, List<AccountResponseDto> accounts) {
}

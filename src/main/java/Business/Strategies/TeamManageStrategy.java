package Business.Strategies;

import DTO.MemberDTO;

public interface TeamManageStrategy {
    public void addTeamMember(MemberDTO dto);

    public void removeTeamMember(MemberDTO dto);

    public void editTeamMember(MemberDTO dto);
}

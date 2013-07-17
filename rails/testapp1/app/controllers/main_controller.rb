class MainController < ApplicationController
  
  def main
    render 'wow/wow'
  end

  def search_user
    name = params[:q]
    @user = User.new(name)
    render :partial => 'wow/output'
  end

  def test_ajax
    hash = {:key1 => 'val1', :key2 => 'val2'}
    render :json => {:success => true, :html => render_to_string(:partial => 'wow/hidden', :layout => false, :locals => {:q => '1'})}
  end

end
